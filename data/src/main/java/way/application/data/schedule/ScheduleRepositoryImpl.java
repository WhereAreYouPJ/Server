package way.application.data.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.core.exception.ServerException;
import way.application.core.utils.ErrorResult;
import way.application.data.member.MemberEntity;
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
    public Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request) {
        MemberEntity createMember = validateUtils.validateMemberEntity(
                request.createMemberId()
        );

        ScheduleEntity savedSchedule = scheduleJpaRepository.save(
                scheduleMapper.toScheduleEntity(request)
        );

        if (request.invitedMemberIds() == null || request.invitedMemberIds().isEmpty()) {
            // ScheduleMember 저장
            scheduleMemberJpaRepository.save(
                    scheduleMemberMapper.toScheduleMemberEntity(
                            savedSchedule, createMember, true, true
                    )
            );
        } else {
            // 예외처리
            List<MemberEntity> invitedMembers = validateUtils.validateMemberEntityIn(
                    request.invitedMemberIds()
            );

            Set<MemberEntity> invitedMembersSet = new HashSet<>(invitedMembers);
            invitedMembersSet.add(createMember);
            invitedMembersSet.forEach(invitedMember -> {
                // ScheduleMember 저장
                scheduleMemberJpaRepository.save(scheduleMemberMapper.toScheduleMemberEntity(
                        savedSchedule, invitedMember, invitedMember.getId().equals(createMember.getId()), invitedMember.getId().equals(createMember.getId())
                ));

                // 푸시 알림
                if (!invitedMember.getId().equals(createMember.getId())) {
                    try {
                        String body = createMember.getUserName() + "이(가) 일정 초대를 했습니다.";
                        fireBaseRepository.sendMessageTo(
                                invitedMember.getFireBaseTargetToken(), "일정 요청이 들어왔습니다.", body
                        );
                    } catch (IOException e) {
                        throw new ServerException(ErrorResult.UNKNOWN_EXCEPTION);
                    }
                }
            });
        }

        return new Schedule.SaveScheduleResponse(savedSchedule.getId());
    }

    @Override
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

}
