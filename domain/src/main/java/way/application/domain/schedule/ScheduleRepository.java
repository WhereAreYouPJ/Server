package way.application.domain.schedule;

public interface ScheduleRepository {
    Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request);
    Schedule.ModifyScheduleResponse modify(Schedule.ModifyScheduleRequest request);
    void delete(Schedule.DeleteScheduleRequest request);
    Schedule.GetScheduleResponse get(Long scheduleId, Long memberId);
}
