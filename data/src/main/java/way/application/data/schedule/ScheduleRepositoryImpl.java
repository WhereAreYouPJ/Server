package way.application.data.schedule;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.core.exception.ServerException;
import way.application.core.utils.ErrorResult;
import way.application.data.member.MemberEntity;
import way.application.data.scheduleMember.ScheduleMemberEntity;
import way.application.data.scheduleMember.ScheduleMemberJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberMapper;
import way.application.data.utils.ValidateUtils;
import way.application.domain.firebase.FireBaseRepository;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleRepositoryImpl implements ScheduleRepository {
	private final ScheduleJpaRepository scheduleJpaRepository;
	private final ScheduleMemberJpaRepository scheduleMemberJpaRepository;
	private final ScheduleMapper scheduleMapper;
	private final ScheduleMemberMapper scheduleMemberMapper;
	private final FireBaseRepository fireBaseRepository;
	private final ValidateUtils validateUtils;

	@Override
	@Transactional
	public Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request) {
		MemberEntity createMember = validateUtils.validateMemberEntity(
			request.createMemberSeq()
		);

		// Schedule 저장
		ScheduleEntity savedSchedule = scheduleJpaRepository.save(
			scheduleMapper.toScheduleEntity(request)
		);

		// ScheduleMember 저장
		Set<MemberEntity> invitedMembers = new HashSet<>(
			validateUtils.validateMemberEntityIn(
				request.invitedMemberSeqs()
			)
		);
		invitedMembers.add(createMember);
		invitedMembers.forEach(invitedMember ->
			saveScheduleMember(
				savedSchedule, invitedMember, createMember
			)
		);

		return new Schedule.SaveScheduleResponse(savedSchedule.getScheduleSeq());
	}

	@Override
	@Transactional
	public Schedule.ModifyScheduleResponse modify(Schedule.ModifyScheduleRequest request) {
		validateUtils.validateMemberEntityIn(request.invitedMemberSeqs());
		validateUtils.validateMemberEntity(request.createMemberSeq());
		validateUtils.validateScheduleEntity(request.scheduleSeq());
		ScheduleEntity scheduleEntity
			= validateUtils.validateScheduleEntityCreatedByMember(request.scheduleSeq(), request.createMemberSeq());

		// 데이터 전체 삭제
		scheduleJpaRepository.deleteById(request.scheduleSeq());
		scheduleMemberJpaRepository.deleteAllBySchedule(scheduleEntity);

		Schedule.SaveScheduleRequest saveScheduleRequest = new Schedule.SaveScheduleRequest(
			request.title(), request.startTime(), request.endTime(),
			request.location(), request.streetName(), request.x(), request.y(),
			request.color(), request.memo(), request.invitedMemberSeqs(), request.createMemberSeq()
		);

		// Schedule + ScheduleMember 다시 저장
		Schedule.SaveScheduleResponse response = save(saveScheduleRequest);

		return new Schedule.ModifyScheduleResponse(response.scheduleSeq());
	}

	@Override
	@Transactional
	public void delete(Schedule.DeleteScheduleRequest request) {
		validateUtils.validateMemberEntity(request.creatorSeq());
		validateUtils.validateScheduleEntity(request.scheduleSeq());
		ScheduleEntity scheduleEntity
			= validateUtils.validateScheduleEntityCreatedByMember(request.scheduleSeq(), request.creatorSeq());

		// 데이터 전체 삭제
		scheduleMemberJpaRepository.deleteAllBySchedule(scheduleEntity);
		scheduleJpaRepository.deleteById(request.scheduleSeq());
	}

	@Override
	@Cacheable(value = "schedules", key = "#scheduleSeq + '-' + #memberSeq")
	@Transactional(readOnly = true)
	public Schedule.GetScheduleResponse get(Long scheduleSeq, Long memberSeq) {
		// 예외처리
		ScheduleEntity scheduleEntity = validateUtils.validateScheduleEntity(scheduleSeq);
		validateUtils.validateMemberEntity(memberSeq);
		validateUtils.validateMemberInScheduleMemberEntity(memberSeq, scheduleSeq);

		// ScheduleMember에서 userName 추출
		List<ScheduleMemberEntity> scheduleMemberEntities
			= scheduleMemberJpaRepository.findAcceptedScheduleMemberByScheduleEntity(scheduleEntity);

		List<String> userName = scheduleMemberEntities.stream()
			.map(sm -> sm.getInvitedMember().getUserName())
			.collect(Collectors.toList());

		return new Schedule.GetScheduleResponse(
			scheduleEntity.getTitle(), scheduleEntity.getStartTime(), scheduleEntity.getEndTime(),
			scheduleEntity.getLocation(), scheduleEntity.getStreetName(), scheduleEntity.getX(), scheduleEntity.getY(),
			scheduleEntity.getColor(), scheduleEntity.getMemo(), userName
		);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule.GetScheduleByDateResponse> getScheduleByDate(Long memberSeq, LocalDate request) {
		validateUtils.validateMemberEntity(memberSeq);

		List<ScheduleEntity> scheduleEntities = scheduleJpaRepository.findAcceptedSchedulesByMemberAndDate(memberSeq,
			request);
		return scheduleEntities.stream()
			.map(scheduleEntity -> new Schedule.GetScheduleByDateResponse(
				scheduleEntity.getScheduleSeq(),
				scheduleEntity.getTitle(),
				scheduleEntity.getLocation(),
				scheduleEntity.getColor(),
				scheduleEntity.getStartTime(),
				scheduleEntity.getEndTime()))
			.collect(Collectors.toList());
	}

	@Override
	public void acceptSchedule(Schedule.AcceptScheduleRequest request) {
		validateUtils.validateMemberEntity(request.memberSeq());
		validateUtils.validateScheduleEntity(request.scheduleSeq());
		ScheduleMemberEntity scheduleMemberEntity
			= validateUtils.validateMemberInSchedule(request.memberSeq(), request.scheduleSeq());

		scheduleMemberEntity.updateAcceptSchedule();

		scheduleMemberJpaRepository.save(scheduleMemberEntity);
	}

	private void saveScheduleMember(ScheduleEntity savedSchedule, MemberEntity invitedMember,
		MemberEntity createMember) {
		boolean isCreator = invitedMember.getMemberSeq().equals(createMember.getMemberSeq());
		scheduleMemberJpaRepository.save(
			scheduleMemberMapper.toScheduleMemberEntity(savedSchedule, invitedMember, isCreator, isCreator));

		if (!isCreator) {
			sendNotification(invitedMember, createMember);
		}
	}

	private void sendNotification(MemberEntity invitedMember, MemberEntity createMember) {
		try {
			String body = createMember.getUserName() + "이(가) 일정 초대를 했습니다.";
			fireBaseRepository.sendMessageTo(invitedMember.getFireBaseTargetToken(), "일정 요청이 들어왔습니다.", body);
		} catch (IOException e) {
			log.info("Exception = {}", e.getMessage());
			throw new ServerException(ErrorResult.FIREBASE_CLOUD_MESSAGING_EXCEPTION);
		}
	}

}
