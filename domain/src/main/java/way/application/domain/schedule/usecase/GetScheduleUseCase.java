package way.application.domain.schedule.usecase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class GetScheduleUseCase {
	private final ScheduleRepository scheduleRepository;

	public Schedule.GetScheduleResponse invoke(Long scheduleSeq, Long memberSeq) {
		return scheduleRepository.get(scheduleSeq, memberSeq);
	}
}
