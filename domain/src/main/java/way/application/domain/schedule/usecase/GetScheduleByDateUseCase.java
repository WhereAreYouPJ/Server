package way.application.domain.schedule.usecase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetScheduleByDateUseCase {
	private final ScheduleRepository scheduleRepository;

	public List<Schedule.GetScheduleByDateResponse> invoke(Long memberSeq, LocalDate request) {
		return scheduleRepository.getScheduleByDate(memberSeq, request);
	}
}
