package way.application.data.scheduleMember;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import way.application.data.member.MemberEntity;
import way.application.data.schedule.ScheduleEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMemberMapper {

    @Mapping(target = "schedule_member_seq", ignore = true)
    ScheduleMemberEntity toScheduleMemberEntity(
            ScheduleEntity schedule, MemberEntity invitedMember, Boolean isCreator, Boolean acceptSchedule
    );
}
