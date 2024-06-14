package way.application.domain.schedule.usecase;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class GetScheduleByMonthUseCase {
	private final ScheduleRepository scheduleRepository;

	public List<Schedule.GetScheduleByMonthResponse> invoke(YearMonth yearMonth, Long memberSeq) {
		return scheduleRepository.getScheduleByMonth(yearMonth, memberSeq);
	}
}
