package way.application.domain.schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request);
    Schedule.ModifyScheduleResponse modify(Schedule.ModifyScheduleRequest request);
    void delete(Schedule.DeleteScheduleRequest request);
    Schedule.GetScheduleResponse get(Long scheduleId, Long memberId);
    List<Schedule.GetScheduleByDateResponse> getScheduleByDate(Long memberId, LocalDateTime request);
}
