package way.application.domain.location;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Location {
	public record SaveLocationRequest(
		@NotNull(message = "memberSeq 값을 입력해주세요")
		Long memberSeq,

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
		Long locationSeq
	) {

	}

	public record DeleteLocationRequest(
		@NotNull(message = "locationSeq 값을 입력해주세요")
		Long locationSeq,

		@NotNull(message = "memberSeq 값을 입력해주세요")
		Long memberSeq
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
