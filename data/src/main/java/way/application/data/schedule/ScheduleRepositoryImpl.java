package way.application.data.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import way.application.core.exception.BadRequestException;
import way.application.core.exception.ServerException;
import way.application.core.utils.ErrorResult;
import way.application.data.member.MemberEntity;
import way.application.data.member.MemberJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberMapper;
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
    private final MemberJpaRepository memberJpaRepository;
    private final ScheduleMemberJpaRepository scheduleMemberJpaRepository;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleMemberMapper scheduleMemberMapper;
    private final FireBaseRepository fireBaseRepository;

    @Override
    public Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request) {
        MemberEntity createMember = memberJpaRepository.findById(request.createMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorResult.USER_BAD_REQUEST_EXCEPTION));

        ScheduleEntity savedSchedule = scheduleJpaRepository.save(scheduleMapper.toScheduleEntity(request));

        if (request.invitedMemberIds() == null || request.invitedMemberIds().isEmpty()) {
            // ScheduleMember 저장
            scheduleMemberJpaRepository.save(
                    scheduleMemberMapper.toScheduleMemberEntity(
                            savedSchedule, createMember, true, true
                    )
            );
        } else {
            // 예외처리
            List<MemberEntity> invitedMembers = memberJpaRepository.findAllById(request.invitedMemberIds());
            if (invitedMembers.size() != request.invitedMemberIds().size()) {
                throw new BadRequestException(ErrorResult.INVITED_MEMBER_BAD_REQUEST_EXCEPTION);
            }

            Set<MemberEntity> invitedMembersSet = new HashSet<>(invitedMembers);
            invitedMembersSet.add(createMember);
            invitedMembersSet.forEach(invitedMember -> {
                // ScheduleMember 저장
                scheduleMemberJpaRepository.save(scheduleMemberMapper.toScheduleMemberEntity(
                        savedSchedule, invitedMember, invitedMember.getId().equals(createMember.getId()), invitedMember.getId().equals(createMember.getId())
                ));

                // 푸시 알림
                if(!invitedMember.getId().equals(createMember.getId())) {
                    try {
                        String body = createMember.getUserName() + "이(가) 일정 초대를 했습니다.";
                        fireBaseRepository.sendMessageTo(invitedMember.getFireBaseTargetToken(), "일정 요청이 들어왔습니다.", body);
                    } catch (IOException e) {
                        throw new ServerException(ErrorResult.UNKNOWN_EXCEPTION);
                    }
                }
            });
        }

        return new Schedule.SaveScheduleResponse(savedSchedule.getId());
    }
}
