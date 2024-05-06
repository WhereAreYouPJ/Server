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
import org.springframework.web.bind.annotation.*;
import way.application.core.base.BaseResponse;
import way.application.domain.member.Member;
import way.application.domain.member.usecase.SaveMemberUseCase;
import way.application.domain.schedule.usecase.SaveScheduleUseCase;

@RestController
@RequestMapping(value = "/v1/member", name = "멤버")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "Response List API")
public class MemberController {
    private final SaveScheduleUseCase saveScheduleUseCase;
    private final SaveMemberUseCase saveMemberUseCase;

    @PostMapping(value = "/join", name = "회원가입")
    @Operation(summary = "join Member API", description = "join Member API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
    })
    public ResponseEntity<BaseResponse> saveMember(@Valid @RequestBody Member.SaveMemberRequest request) {
        saveMemberUseCase.invoke(request);

        return ResponseEntity.ok().build();
    }

}
