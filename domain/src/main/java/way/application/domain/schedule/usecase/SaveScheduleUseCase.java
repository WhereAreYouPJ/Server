package way.application.domain.schedule.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

@Component
@RequiredArgsConstructor
@Transactional
public class SaveScheduleUseCase {
    private final ScheduleRepository scheduleRepository;

    public Schedule.SaveScheduleResponse invoke(Schedule.SaveScheduleRequest request) {
        return scheduleRepository.save(request);
    }
}
