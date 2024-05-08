package way.application.data.schedule;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
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
                request.createMemberId()
        );

        // Schedule 저장
        ScheduleEntity savedSchedule = scheduleJpaRepository.save(
                scheduleMapper.toScheduleEntity(request)
        );

        // ScheduleMember 저장
        Set<MemberEntity> invitedMembers = new HashSet<>(
                validateUtils.validateMemberEntityIn(
                        request.invitedMemberIds()
                )
        );
        invitedMembers.add(createMember);
        invitedMembers.forEach(invitedMember ->
                saveScheduleMember(
                        savedSchedule, invitedMember, createMember
                )
        );

        return new Schedule.SaveScheduleResponse(savedSchedule.getId());
    }

    @Override
    @Transactional
    public Schedule.ModifyScheduleResponse modify(Schedule.ModifyScheduleRequest request) {
        ScheduleEntity scheduleEntity = validateUtils.validateScheduleEntity(request.id());

        // 데이터 전체 삭제
        scheduleJpaRepository.deleteById(request.id());
        scheduleMemberJpaRepository.deleteAllBySchedule(scheduleEntity);

        Schedule.SaveScheduleRequest saveScheduleRequest = new Schedule.SaveScheduleRequest(
                request.title(), request.startTime(), request.endTime(), request.location(),
                request.color(), request.memo(), request.invitedMemberIds(), request.createMemberId()
        );

        // Schedule + ScheduleMember 다시 저장
        Schedule.SaveScheduleResponse response = save(saveScheduleRequest);

        return new Schedule.ModifyScheduleResponse(response.id());
    }

    @Override
    @Transactional
    public void delete(Schedule.DeleteScheduleRequest request) {
        ScheduleEntity scheduleEntity = validateUtils.validateScheduleEntity(request.id());

        // 데이터 전체 삭제
        scheduleMemberJpaRepository.deleteAllBySchedule(scheduleEntity);
        scheduleJpaRepository.deleteById(request.id());
    }

    @Override
    @Transactional(readOnly = true)
    public Schedule.GetScheduleResponse get(Long scheduleId, Long memberId) {
        // 예외처리
        ScheduleEntity scheduleEntity = validateUtils.validateScheduleEntity(scheduleId);
        validateUtils.validateMemberEntity(memberId);
        validateUtils.validateMemberInScheduleMemberEntity(memberId, scheduleId);

        // ScheduleMember에서 userName 추출
        List<ScheduleMemberEntity> scheduleMemberEntities
                = scheduleMemberJpaRepository.findAcceptedScheduleMemberByScheduleEntity(scheduleEntity);

        List<String> userName = scheduleMemberEntities.stream()
                .map(sm -> sm.getInvitedMember().getUserName())
                .collect(Collectors.toList());

        return new Schedule.GetScheduleResponse(
                scheduleEntity.getTitle(), scheduleEntity.getStartTime(), scheduleEntity.getEndTime(), scheduleEntity.getLocation(),
                scheduleEntity.getColor(), scheduleEntity.getMemo(), userName
        );
    }


    private void saveScheduleMember(ScheduleEntity savedSchedule, MemberEntity invitedMember, MemberEntity createMember) {
        boolean isCreator = invitedMember.getId().equals(createMember.getId());
        scheduleMemberJpaRepository.save(scheduleMemberMapper.toScheduleMemberEntity(savedSchedule, invitedMember, isCreator, isCreator));

        if (!isCreator) {
            sendNotification(invitedMember, createMember);
        }
    }

    private void sendNotification(MemberEntity invitedMember, MemberEntity createMember) {
        try {
            String body = createMember.getUserName() + "이(가) 일정 초대를 했습니다.";
            fireBaseRepository.sendMessageTo(invitedMember.getFireBaseTargetToken(), "일정 요청이 들어왔습니다.", body);
        } catch (IOException e) {
            throw new ServerException(ErrorResult.FIREBASE_CLOUD_MESSAGING_EXCEPTION);
        }
    }

}
