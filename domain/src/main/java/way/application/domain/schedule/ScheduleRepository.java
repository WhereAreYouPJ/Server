package way.application.domain.schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
	Schedule.SaveScheduleResponse save(Schedule.SaveScheduleRequest request);

	Schedule.ModifyScheduleResponse modify(Schedule.ModifyScheduleRequest request);

	void delete(Schedule.DeleteScheduleRequest request);

	Schedule.GetScheduleResponse get(Long scheduleSeq, Long memberSeq);

	List<Schedule.GetScheduleByDateResponse> getScheduleByDate(Long memberSeq, LocalDateTime request);

	void acceptSchedule(Schedule.AcceptScheduleRequest request);
}
