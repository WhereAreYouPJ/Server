package way.application.domain.schedule;

public interface ScheduleRepository {
    Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request);
}
