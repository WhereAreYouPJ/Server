package way.application.domain.schedule.usecase;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class AcceptScheduleUseCase {
	private final ScheduleRepository scheduleRepository;

	public void invoke(Schedule.AcceptScheduleRequest request) {
		scheduleRepository.acceptSchedule(request);
	}
}
