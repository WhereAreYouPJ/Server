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
import way.application.domain.schedule.usecase.DeleteScheduleUseCase;
import way.application.domain.schedule.usecase.GetScheduleUseCase;
import way.application.domain.schedule.usecase.ModifyScheduleUseCase;
import way.application.domain.schedule.usecase.SaveScheduleUseCase;

@RestController
@RequestMapping(value = "/v1/schedule", name = "일정")
@RequiredArgsConstructor
@Tag(name = "Schedule API", description = "업무 담당: 박종훈")
public class ScheduleController {
    private final SaveScheduleUseCase saveScheduleUseCase;
    private final ModifyScheduleUseCase modifyScheduleUseCase;
    private final DeleteScheduleUseCase deleteScheduleUseCase;
    private final GetScheduleUseCase getScheduleUseCase;

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
                    description = "SERVER_ERROR 500",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "S501",
                    description = "FIREBASE_CLOUD_MESSAGING_EXCEPTION 500",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "Invalid DTO Parameter errors 400",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "UB002",
                    description = "USER_BAD_REQUEST_EXCEPTION 400",
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
                    description = "SERVER_ERROR 500",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "S501",
                    description = "FIREBASE_CLOUD_MESSAGING_EXCEPTION 500",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "Invalid DTO Parameter errors 400",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "UB002",
                    description = "USER_BAD_REQUEST_EXCEPTION 400",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "SIB003",
                    description = "SCHEDULE_ID_BAD_REQUEST_EXCEPTION 400",
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
                    description = "SERVER_ERROR 500",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "Invalid DTO Parameter errors 400",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "SIB003",
                    description = "SCHEDULE_ID_BAD_REQUEST_EXCEPTION 400",
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
                    description = "SERVER_ERROR 500",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "Invalid DTO Parameter errors 400",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "SIB003",
                    description = "SCHEDULE_ID_BAD_REQUEST_EXCEPTION 400",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "UIB002",
                    description = "USER_ID_BAD_REQUEST_EXCEPTION 400",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "MINISB004",
                    description = "MEMBER_ID_NOT_IN_SCHEDULE_BAD_EXCEPTION 400",
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
}
