package way.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import way.application.core.base.BaseResponse;
import way.application.core.exception.GlobalExceptionHandler;
import way.application.domain.schedule.Schedule;
import way.application.domain.schedule.usecase.SaveScheduleUseCase;

@RestController
@RequestMapping(value = "/v1/schedule", name = "일정")
@RequiredArgsConstructor
@Tag(name = "Schedule API", description = "Response List API")
public class ScheduleController {
    private final SaveScheduleUseCase saveScheduleUseCase;

    @PostMapping(value = "/save", name = "일정 생성")
    @Operation(summary = "Save Schedule API", description = "Create Schedule API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request Exception", content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> saveSchedule(@Valid @RequestBody Schedule.SaveScheduleRequest request) {
        Schedule.SaveScheduleResponse response = saveScheduleUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }
}
