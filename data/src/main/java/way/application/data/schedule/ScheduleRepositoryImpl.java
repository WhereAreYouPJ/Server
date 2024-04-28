package way.application.data.schedule;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import way.application.core.exception.BadRequestException;
import way.application.core.utils.ErrorResult;
import way.application.data.member.MemberEntity;
import way.application.data.member.MemberJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberEntity;
import way.application.data.scheduleMember.ScheduleMemberJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberMapper;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

import java.util.List;

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
                .orElseThrow(() -> new BadRequestException(ErrorResult.BAD_REQUEST_EXCEPTION));

        ScheduleEntity savedSchedule = scheduleJpaRepository.save(
                scheduleMapper.toScheduleEntity(request)
        );

        List<MemberEntity> invitedMembers = memberJpaRepository.findAllById(request.invitedMemberIds());
        if (invitedMembers.size() != request.invitedMemberIds().size()) {
            throw new BadRequestException(ErrorResult.BAD_REQUEST_EXCEPTION);
        }

        invitedMembers.forEach(invitedMember -> scheduleMemberJpaRepository.save(
                scheduleMemberMapper.toScheduleMemberEntity(
                        savedSchedule, invitedMember, invitedMember.getId().equals(createMember.getId())
                )
        ));

        return new Schedule.SaveScheduleResponse(savedSchedule.getId());
    }
}
