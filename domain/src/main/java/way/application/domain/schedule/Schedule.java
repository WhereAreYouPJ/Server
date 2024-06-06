package way.application.domain.schedule;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
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

		List<Long> invitedMemberSeqs,

		@NotNull(message = "createMemberSeq 값을 입력해주세요")
		Long createMemberSeq
	) {

	}

	public record SaveScheduleResponse(
		Long scheduleSeq
	) {

	}

	public record ModifyScheduleRequest(
		@NotNull(message = "scheduleSeq 값을 입력해주세요.")
		Long scheduleSeq,

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

		List<Long> invitedMemberSeqs,

		@NotNull(message = "createMemberSeq 값을 입력해주세요")
		Long createMemberSeq
	) {

	}

	public record ModifyScheduleResponse(
		Long scheduleSeq
	) {

	}

	public record DeleteScheduleRequest(
		@NotNull(message = "scheduleSeq 값을 입력해주세요.")
		Long scheduleSeq,

		@NotNull(message = "creatorSeq 값을 입력해주세요.")
		Long creatorSeq
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
	) implements Serializable {
		private static final long serialVersionUID = 1L;
	}

	public record GetScheduleByDateResponse(
		Long id,

		String title,

		String location,

		String color
	) {

	}

	public record AcceptScheduleRequest(
		Long scheduleSeq,
		Long memberSeq
	) {

	}
}
