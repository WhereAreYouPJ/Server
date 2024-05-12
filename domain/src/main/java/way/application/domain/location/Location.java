package way.application.domain.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Location {
    public record SaveLocationRequest(
            @NotNull(message = "memberId 값을 입력해주세요")
            Long memberId,

            @NotEmpty(message = "location 값을 입력해주세요.")
            String location,

            @NotEmpty(message = "streetName 값을 입력해주세요.")
            String streetName,

            @NotNull(message = "x 값을 입력해주세요.")
            Double x,

            @NotNull(message = "y 값을 입력해주세요.")
            Double y

    ) {

    }

    public record SaveLocationResponse(
            Long id
    ) {

    }

    public record DeleteLocationRequest(
            @NotNull(message = "locationId 값을 입력해주세요")
            Long locationId,

            @NotNull(message = "memberId 값을 입력해주세요")
            Long memberId
    ) {

    }

    public record GetLocationResponse(
            String location,

            String streetName,

            Double x,

            Double y
    ) {

    }
}
