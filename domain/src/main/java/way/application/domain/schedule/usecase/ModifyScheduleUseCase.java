package way.application.domain.schedule.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class ModifyScheduleUseCase {
	private final ScheduleRepository scheduleRepository;

	public Schedule.ModifyScheduleResponse invoke(Schedule.ModifyScheduleRequest request) {
		return scheduleRepository.modify(request);
	}
}
