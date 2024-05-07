package way.application.domain.schedule;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class Schedule {
    public record SaveScheduleRequest(
            @NotEmpty(message = "title 값을 입력해주세요.")
            String title,

            @NotNull(message = "startTime 값을 입력해주세요.")
            LocalDateTime startTime,

            @NotNull(message = "endTime 값을 입력해주세요.")
            LocalDateTime endTime,

            @NotEmpty(message = "location 값을 입력해주세요.")
            String location,

            @NotEmpty(message = "color 값을 입력해주세요.")
            String color,

            @NotEmpty(message = "memo 값을 입력해주세요.")
            String memo,

            List<Long> invitedMemberIds,

            @NotNull(message = "createMemberId 값을 입력해주세요")
            Long createMemberId
    ) {

    }

    public record SaveScheduleResponse(
            Long id
    ) {

    }

    public record ModifyScheduleRequest(
            @NotNull(message = "id 값을 입력해주세요.")
            Long id,

            @NotEmpty(message = "title 값을 입력해주세요.")
            String title,

            @NotNull(message = "startTime 값을 입력해주세요.")
            LocalDateTime startTime,

            @NotNull(message = "endTime 값을 입력해주세요.")
            LocalDateTime endTime,

            @NotEmpty(message = "location 값을 입력해주세요.")
            String location,

            @NotEmpty(message = "color 값을 입력해주세요.")
            String color,

            @NotEmpty(message = "memo 값을 입력해주세요.")
            String memo,

            List<Long> invitedMemberIds,

            @NotNull(message = "createMemberId 값을 입력해주세요")
            Long createMemberId
    ) {

    }

    public record ModifyScheduleResponse(
            Long id
    ) {

    }

    public record DeleteScheduleRequest(
            @NotNull(message = "id 값을 입력해주세요.")
            Long id
    ) {

    }
}
