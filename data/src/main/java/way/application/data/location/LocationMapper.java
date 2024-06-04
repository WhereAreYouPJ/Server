package way.application.data.location;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import way.application.data.member.MemberEntity;
import way.application.data.schedule.ScheduleEntity;
import way.application.domain.location.Location;
import way.application.domain.schedule.Schedule;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {
	@Mapping(target = "locationSeq", ignore = true)
	LocationEntity toLocationEntity(Location.SaveLocationRequest request, MemberEntity member);
}
