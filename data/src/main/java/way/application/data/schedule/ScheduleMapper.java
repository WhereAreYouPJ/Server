package way.application.data.schedule;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import way.application.domain.schedule.Schedule;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {
	@Mapping(target = "scheduleSeq", ignore = true)
	ScheduleEntity toScheduleEntity(Schedule.SaveScheduleRequest request);

	Schedule.GetScheduleByMonthResponse toGetScheduleByMonthResponse(ScheduleEntity scheduleEntity);
}
