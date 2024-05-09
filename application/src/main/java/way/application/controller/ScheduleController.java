package way.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import way.application.core.base.BaseResponse;
import way.application.core.exception.GlobalExceptionHandler;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.usecase.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/schedule", name = "일정")
@RequiredArgsConstructor
@Tag(name = "Schedule API", description = "업무 담당: 박종훈")
public class ScheduleController {
    private final SaveScheduleUseCase saveScheduleUseCase;
    private final ModifyScheduleUseCase modifyScheduleUseCase;
    private final DeleteScheduleUseCase deleteScheduleUseCase;
    private final GetScheduleUseCase getScheduleUseCase;
    private final GetScheduleByDateUseCase getScheduleByDateUseCase;

    @PostMapping(name = "일정 생성")
    @Operation(summary = "Create Schedule API", description = "Create Schedule API")
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
                    description = "SERVER_ERROR 500 (나도 몰라 ..)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "S501",
                    description = "FIREBASE_CLOUD_MESSAGING_EXCEPTION 500 / FIREBASE 오류(서버 오류 혹은 Token 존재 X)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "Invalid DTO Parameter errors 400 / 요청 값 형식 요류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "UIB002",
                    description = "USER_ID_BAD_REQUEST_EXCEPTION 400 / USER_ID 오류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> saveSchedule(@Valid @RequestBody Schedule.SaveScheduleRequest request) {
        Schedule.SaveScheduleResponse response = saveScheduleUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }

    @PutMapping(name = "일정 수정")
    @Operation(summary = "Modify Schedule API", description = "Modify Schedule API")
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
                    description = "SERVER_ERROR 500 (나도 몰라 ..)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "S501",
                    description = "FIREBASE_CLOUD_MESSAGING_EXCEPTION 500 / FIREBASE 오류(서버 오류 혹은 Token 존재 X)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "Invalid DTO Parameter errors 400 / 요청 값 형식 요류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "UIB002",
                    description = "USER_ID_BAD_REQUEST_EXCEPTION 400 / USER_ID 오류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "SIB003",
                    description = "SCHEDULE_ID_BAD_REQUEST_EXCEPTION 400 / SCHEDULE_ID 오류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> modifySchedule(@Valid @RequestBody Schedule.ModifyScheduleRequest request) {
        Schedule.ModifyScheduleResponse response = modifyScheduleUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }

    @DeleteMapping(name = "일정 삭제")
    @Operation(summary = "Delete Schedule API", description = "Delete Schedule API")
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
                    description = "SERVER_ERROR 500 (나도 몰라 ..)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "Invalid DTO Parameter errors 400 / 요청 값 형식 요류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "SIB003",
                    description = "SCHEDULE_ID_BAD_REQUEST_EXCEPTION 400 / SCHEDULE_ID 오류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> deleteSchedule(@Valid @RequestBody Schedule.DeleteScheduleRequest request) {
        deleteScheduleUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
    }

    @GetMapping(name = "일정 조회")
    @Operation(summary = "Get Schedule API", description = "Get Schedule API")
    @Parameters({
            @Parameter(
                    name = "scheduleId",
                    description = "Schedule PK",
                    example = "1"),
            @Parameter(
                    name = "memberId",
                    description = "memberId PK",
                    example = "1")
    })
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
                    description = "SERVER_ERROR 500 (나도 몰라 ..)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "SIB003",
                    description = "SCHEDULE_ID_BAD_REQUEST_EXCEPTION 400 / SCHEDULE_ID 오류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "UB002",
                    description = "USER_ID_BAD_REQUEST_EXCEPTION 400 / USER_ID 오류",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "MINISB004",
                    description = "MEMBER_ID_NOT_IN_SCHEDULE_BAD_EXCEPTION 400 / 일정에 존재하지 않는 Member의 경우 + Schedule에서 일정을 수락하지 않은 경우 조회 불가",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> getSchedule(
            @Valid
            @RequestParam(name = "scheduleId") Long scheduleId,
            @RequestParam(name = "memberId") Long memberId) {
        Schedule.GetScheduleResponse response = getScheduleUseCase.invoke(scheduleId, memberId);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }

    @GetMapping(value = "/date", name = "해당 날짜 일정 조회")
    @Operation(summary = "Get Schedule By Date API", description = "Get Schedule By Date API")
    @Parameters({
            @Parameter(
                    name = "date",
                    description = "조회하려는 날짜",
                    example = "2024-05-09T09:12:21.556Z")
    })
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
                    description = "SERVER_ERROR 500 (나도 몰라 ..)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> getScheduleByDate(
            @Valid
            @RequestParam(name = "date") LocalDateTime date) {
        List<Schedule.GetScheduleByDateResponse> response = getScheduleByDateUseCase.invoke(date);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }
}
