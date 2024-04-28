package way.application.domain.schedule;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class Schedule {
    public record Request(
            @NotEmpty(message = "값을 입력해주세요.")
            String title,

            @NotEmpty(message = "값을 입력해주세요.")
            LocalDateTime startTime,

            @NotEmpty(message = "값을 입력해주세요.")
            LocalDateTime endTime,

            @NotEmpty(message = "값을 입력해주세요.")
            String location,

            @NotEmpty(message = "값을 입력해주세요.")
            String color,

            @NotEmpty(message = "값을 입력해주세요.")
            String memo
    ) {

    }
}
