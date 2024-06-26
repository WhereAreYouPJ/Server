package way.application.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import way.application.core.base.BaseResponse;
import way.application.core.exception.GlobalExceptionHandler;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.usecase.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/schedule", name = "일정")
@RequiredArgsConstructor
@Tag(name = "Schedule API", description = "업무 담당: 박종훈")
@OpenAPIDefinition(servers = {@Server(url = "/", description = "https://wlrmadjel.com")})
public class ScheduleController {
	private final SaveScheduleUseCase saveScheduleUseCase;
	private final ModifyScheduleUseCase modifyScheduleUseCase;
	private final DeleteScheduleUseCase deleteScheduleUseCase;
	private final GetScheduleUseCase getScheduleUseCase;
	private final GetScheduleByDateUseCase getScheduleByDateUseCase;
	private final AcceptScheduleUseCase acceptScheduleUseCase;
	private final GetScheduleByMonthUseCase getScheduleByMonthUseCase;

	@PostMapping(name = "일정 생성")
	@Operation(summary = "일정 생성 API", description = "일정 생성 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S501",
			description = "500 FIREBASE_CLOUD_MESSAGING_EXCEPTION / FIREBASE 오류(서버 오류 혹은 Token 존재 X)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors / 요청 값 형식 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSB002",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / MEMBER_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse<Schedule.SaveScheduleResponse>> saveSchedule(@Valid @RequestBody Schedule.SaveScheduleRequest request) {
		Schedule.SaveScheduleResponse response = saveScheduleUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@PutMapping(name = "일정 수정")
	@Operation(summary = "일정 수정 API", description = "일정 수정 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S501",
			description = "500 FIREBASE_CLOUD_MESSAGING_EXCEPTION / FIREBASE 오류(서버 오류 혹은 Token 존재 X)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors / 요청 값 형식 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSB002",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / MEMBER_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "SSB003",
			description = "400 SCHEDULE_SEQ_BAD_REQUEST_EXCEPTION / SCHEDULE_ID 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "SDCBMB008",
			description = "400 SCHEDULE_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION / SCHEDULE_ID 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse<Schedule.ModifyScheduleResponse>> modifySchedule(@Valid @RequestBody Schedule.ModifyScheduleRequest request) {
		Schedule.ModifyScheduleResponse response = modifyScheduleUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@DeleteMapping(name = "일정 삭제")
	@Operation(summary = "일정 삭제 API", description = "일정 삭제 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors / 요청 값 형식 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "SSB003",
			description = "400 SCHEDULE_SEQ_BAD_REQUEST_EXCEPTION / SCHEDULE_ID 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSB002",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / MEMBER_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "SDCBMB008",
			description = "400 SCHEDULE_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION / SCHEDULE_ID 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> deleteSchedule(@Valid @RequestBody Schedule.DeleteScheduleRequest request) {
		deleteScheduleUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@GetMapping(name = "일정 조회")
	@Operation(summary = "일정 상세 조회 API", description = "일정 상세 조회 API")
	@Parameters({
		@Parameter(
			name = "scheduleSeq",
			description = "Schedule Sequence",
			example = "1"),
		@Parameter(
			name = "memberSeq",
			description = "Member Sequence",
			example = "1")
	})
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "SSB003",
			description = "400 SCHEDULE_SEQ_BAD_REQUEST_EXCEPTION / SCHEDULE_ID 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSB002",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / MEMBER_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSNISB004",
			description = "400 MEMBER_SEQ_NOT_IN_SCHEDULE_BAD_REQUEST_EXCEPTION / 일정에 존재하지 않는 Member의 경우 + Schedule에서 일정을 수락하지 않은 경우 조회 불가",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse<Schedule.GetScheduleResponse>> getSchedule(
		@Valid
		@RequestParam(name = "scheduleSeq") Long scheduleSeq,
		@RequestParam(name = "memberSeq") Long memberSeq) {
		Schedule.GetScheduleResponse response = getScheduleUseCase.invoke(scheduleSeq, memberSeq);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@GetMapping(value = "/date", name = "해당 날짜 일정 조회")
	@Operation(summary = "해당 날짜 일정 조회 API", description = "해당 날짜 일정 조회 API")
	@Parameters({
		@Parameter(
			name = "date",
			description = "조회하려는 날짜",
			example = "2024-05-12"),
		@Parameter(
			name = "memberSeq",
			description = "Member Sequence",
			example = "1")
	})
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSB002",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / MEMBER_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse<List<Schedule.GetScheduleByDateResponse>>> getScheduleByDate(
		@Valid
		@RequestParam(name = "date") LocalDate date,
		@RequestParam(name = "memberSeq") Long memberSeq) {
		List<Schedule.GetScheduleByDateResponse> response = getScheduleByDateUseCase.invoke(memberSeq, date);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@PostMapping(value = "/accept-schedule", name = "일정 초대 수락")
	@Operation(summary = "일정 초대 수락 API", description = "일정 초대 수락 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSB002",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / MEMBER_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "SSB003",
			description = "400 SCHEDULE_SEQ_BAD_REQUEST_EXCEPTION / SCHEDULE_ID 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSNISB004",
			description = "400 MEMBER_SEQ_NOT_IN_SCHEDULE_BAD_REQUEST_EXCEPTION / 일정에 존재하지 않는 Member의 경우 + Schedule에서 일정을 수락하지 않은 경우 조회 불가",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> acceptSchedule(
		@Valid @RequestBody Schedule.AcceptScheduleRequest request) {
		acceptScheduleUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@GetMapping(value = "/month-schedule", name = "월별 일정 조회")
	@Operation(summary = "월별 일정 조회 API", description = "월별 일정 조회 API")
	@Parameters({
		@Parameter(
			name = "yearMonth",
			description = "조회하려는 날짜(yyyy-dd)",
			example = "2024-05"),
		@Parameter(
			name = "memberSeq",
			description = "Member Sequence",
			example = "1")
	})
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true
			// content = @Content(
			// 	mediaType = "application/json",
			// 	schema = @Schema(
			// 		implementation = Schedule.GetScheduleByMonthResponse.class,
			// 		type = "array"))
		),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "MSB002",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / MEMBER_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse<List<Schedule.GetScheduleByMonthResponse>>> getScheduleByMonth(
		@Valid @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
		@RequestParam("memberSeq") Long memberSeq) {
		List<Schedule.GetScheduleByMonthResponse> response = getScheduleByMonthUseCase.invoke(yearMonth, memberSeq);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}
}
