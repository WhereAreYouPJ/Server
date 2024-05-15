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

            @NotEmpty(message = "streetName 값을 입력해주세요.")
            String streetName,

            @NotNull(message = "x 값을 입력해주세요.")
            Double x,

            @NotNull(message = "y 값을 입력해주세요.")
            Double y,

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

            @NotEmpty(message = "streetName 값을 입력해주세요.")
            String streetName,

            @NotNull(message = "x 값을 입력해주세요.")
            Double x,

            @NotNull(message = "y 값을 입력해주세요.")
            Double y,

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
            @NotNull(message = "scheduleId 값을 입력해주세요.")
            Long scheduleId,

            @NotNull(message = "creatorId 값을 입력해주세요.")
            Long creatorId
    ) {

    }

    public record GetScheduleResponse(
            String title,

            LocalDateTime startTime,

            LocalDateTime endTime,

            String location,

            String streetName,

            Double x,

            Double y,

            String color,

            String memo,

            List<String> userName
    ) {

    }

    public record GetScheduleByDateResponse(
            Long id,

            String title,

            String location,

            String color
    ) {

    }
}
