package way.application.domain.schedule.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class GetScheduleUseCase {
    private final ScheduleRepository scheduleRepository;

    public Schedule.GetScheduleResponse invoke(Long scheduleId, Long memberId) {
        scheduleRepository.get(scheduleId, memberId);
    }
}
