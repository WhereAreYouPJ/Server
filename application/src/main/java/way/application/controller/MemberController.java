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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import way.application.core.base.BaseResponse;
import way.application.core.exception.GlobalExceptionHandler;
import way.application.domain.member.Member;
import way.application.domain.member.usecase.*;

@RestController
@RequestMapping(value = "/v1/member", name = "멤버")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "업무 담당: 송인준")
@OpenAPIDefinition(servers = {@Server(url = "/", description = "https://wlrmadjel.com")})
public class MemberController {
    private final SaveMemberUseCase saveMemberUseCase;
    private final CheckIdUseCase checkIdUseCase;
    private final CheckEmailUseCase checkEmailUseCase;
    private final LoginUseCase loginUseCase;
    private final SendMailUseCase sendMailUseCase;

    @PostMapping(name = "회원가입")
    @Operation(summary = "join Member API", description = "join Member API")
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
                    description = "500 SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "B001",
                    description = "400 Invalid DTO Parameter errors",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> saveMember(@Valid @RequestBody Member.SaveMemberRequest request) {
        saveMemberUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
    }

    @GetMapping(value ="/checkId",name = "아이디 중복 체크")
    @Operation(summary = "Check Id API", description = "Check Id API")
    @Parameters({
            @Parameter(
                    name = "userId",
                    description = "userId",
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
                    responseCode = "400",
                    description = "B001 Invalid DTO Parameter errors",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "S500 SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "UID001 USER_ID_DUPLICATION_EXCEPTION",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> checkId(@Valid @RequestParam("userId") Member.CheckIdRequest request) {

        Member.CheckIdResponse response = checkIdUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }

    @GetMapping(value = "checkEmail",name = "이메일 중복 체크")
    @Operation(summary = "Check Email API", description = "Check Email API")
    @Parameters({
            @Parameter(
                    name = "email",
                    description = "email",
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
                    responseCode = "400",
                    description = "B001 Invalid DTO Parameter errors",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "S500 SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "ED002 EMAIL_DUPLICATION_EXCEPTION",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> checkEmail(@Valid @RequestParam("email") Member.CheckEmailRequest request) {

        Member.CheckEmailResponse response = checkEmailUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }

    @PostMapping(value = "/login",name = "로그인")
    @Operation(summary = "Login API", description = "Login API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = BaseResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "B001 Invalid DTO Parameter errors",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "S500 SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "UIB009 USER_ID_BAD_REQUEST_EXCEPTION",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "PB005 PASSWORD_BAD_REQUEST_EXCEPTION",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody Member.MemberLoginRequest request) {

        Member.MemberLoginResponse response = loginUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
    }

    @PostMapping(value ="/email/send",name = "메일 전송")
    @Operation(summary = "Mail Send API", description = "Mail Send API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = BaseResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "B001 Invalid DTO Parameter errors",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "S500 SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "EB009 Invalid Email errors",
                    content = @Content(
                            schema = @Schema(
                                    implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public ResponseEntity<BaseResponse> sendEmail(@Valid @RequestBody Member.MailSendRequest request) {

        sendMailUseCase.invoke(request);

        return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
    }
}
