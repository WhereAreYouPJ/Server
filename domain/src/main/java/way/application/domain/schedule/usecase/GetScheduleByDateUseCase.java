package way.application.domain.schedule.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetScheduleByDateUseCase {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule.GetScheduleByDateResponse> invoke(Long memberId, LocalDateTime request) {
        return scheduleRepository.getScheduleByDate(memberId, request);
    }
}
