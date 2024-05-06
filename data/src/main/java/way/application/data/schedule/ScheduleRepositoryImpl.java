package way.application.data.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import way.application.core.exception.BadRequestException;
import way.application.core.utils.ErrorResult;
import way.application.data.member.MemberEntity;
import way.application.data.member.MemberJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberMapper;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

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

    @Override
    public Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request) {
        MemberEntity createMember = memberJpaRepository.findById(request.createMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorResult.USER_BAD_REQUEST_EXCEPTION));

        ScheduleEntity savedSchedule = scheduleJpaRepository.save(scheduleMapper.toScheduleEntity(request));

        if (request.invitedMemberIds() == null || request.invitedMemberIds().isEmpty()) {
            scheduleMemberJpaRepository.save(scheduleMemberMapper.toScheduleMemberEntity(savedSchedule, createMember, true));
        } else {
            List<MemberEntity> invitedMembers = memberJpaRepository.findAllById(request.invitedMemberIds());
            if (invitedMembers.size() != request.invitedMemberIds().size()) {
                throw new BadRequestException(ErrorResult.INVITED_MEMBER_BAD_REQUEST_EXCEPTION);
            }

            Set<MemberEntity> invitedMembersSet = new HashSet<>(invitedMembers);
            invitedMembersSet.add(createMember);
            invitedMembersSet.forEach(invitedMember -> {
                scheduleMemberJpaRepository.save(scheduleMemberMapper.toScheduleMemberEntity(
                        savedSchedule, invitedMember, invitedMember.getId().equals(createMember.getId())
                ));
            });
        }

        return new Schedule.SaveScheduleResponse(savedSchedule.getId());
    }
}
