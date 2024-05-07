package way.application.domain.schedule.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class DeleteScheduleUseCase {
    private final ScheduleRepository scheduleRepository;

    public void invoke(Schedule.DeleteScheduleRequest request) {
        scheduleRepository.delete(request);
    }
}
