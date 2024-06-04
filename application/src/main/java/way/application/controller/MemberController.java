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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
	private final CodeVerifyUseCase codeVerifyUseCase;
	private final ResetPasswordUseCase resetPasswordUseCase;
	private final FindIdUseCase findIdUseCase;
	private final GetMemberDetailUseCase getMemberDetailUseCase;
	private final ModifyUserInfoUseCase modifyUserInfoUseCase;
	private final GetMemberDetailByUserIdUseCase getMemberDetailByUserIdUseCase;
	private final LogoutUseCase logoutUseCase;
	private final VerifyPasswordCodeUseCase verifyPasswordCodeUseCase;

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

	@GetMapping(value = "/checkId", name = "아이디 중복 체크")
	@Operation(summary = "Check Id API", description = "Check Id API")
	@Parameters({
		@Parameter(
			name = "userId",
			description = "userId",
			example = "dlswns97")
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
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "UID001",
			description = "409 USER_ID_DUPLICATION_EXCEPTION",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> checkId(@Valid @RequestParam("userId") String userId) {

		Member.CheckIdResponse response = checkIdUseCase.invoke(userId);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@GetMapping(value = "checkEmail", name = "이메일 중복 체크")
	@Operation(summary = "Check Email API", description = "Check Email API")
	@Parameters({
		@Parameter(
			name = "email",
			description = "email",
			example = "dlswns@whereareyou.com")
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
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "ED002",
			description = "409 EMAIL_DUPLICATION_EXCEPTION",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> checkEmail(@Valid @RequestParam("email") String email) {

		Member.CheckEmailResponse response = checkEmailUseCase.invoke(email);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@PostMapping(value = "/login", name = "로그인")
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
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "UIB009",
			description = "400 USER_ID_BAD_REQUEST_EXCEPTION",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "PB005",
			description = "400 PASSWORD_BAD_REQUEST_EXCEPTION",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> login(@Valid @RequestBody Member.MemberLoginRequest request) {

		Member.MemberLoginResponse response = loginUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@PostMapping(value = "/email/send", name = "메일 전송")
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
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "EB009",
			description = "400 Invalid Email errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> sendEmail(@Valid @RequestBody Member.MailSendRequest request) {

		sendMailUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@PostMapping(value = "/email/verify", name = "인증코드 검증 ")
	@Operation(summary = "Code Verify API", description = "Code Verify API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "EB009",
			description = "400 Invalid Email errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "CB011",
			description = "400 Invalid Code errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> verifyCode(@Valid @RequestBody Member.CodeVerifyRequest request) {

		codeVerifyUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@PostMapping(value = "/email/verifyPassword", name = "비밀번호 재설정 인증코드 검증 ")
	@Operation(summary = "Password Code Verify API", description = "Password Code Verify API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "EB009",
			description = "400 Invalid Email errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "CB011",
			description = "400 Invalid Code errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "UMB012",
			description = "400 User Mismatch errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> verifyPasswordCode(
		@Valid @RequestBody Member.VerifyPasswordCodeRequest request) {

		verifyPasswordCodeUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@PostMapping(value = "/resetPassword", name = "비밀번호 재설정")
	@Operation(summary = "Password Code Verify API", description = "Password Code Verify API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "PB005",
			description = "400 Invalid Password errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "PMB013",
			description = "400 Password Mismatch errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "UIB009",
			description = "400 USER_ID_BAD_REQUEST_EXCEPTION",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> resetPassword(@Valid @RequestBody Member.PasswordResetRequest request) {

		resetPasswordUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@PostMapping(value = "/findId", name = "아이디 찾기")
	@Operation(summary = "Find Id API", description = "Find Id API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "EB009",
			description = "400 Invalid Email errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "CB011",
			description = "400 Invalid Code errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> findId(@Valid @RequestBody Member.FindIdRequest request) {

		Member.FindIdResponse findIdResponse = findIdUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(findIdResponse));
	}

	@GetMapping(value = "/details", name = "회원 상세 정보")
	@Operation(summary = "Get Member Details API", description = "Get Member Details API")
	@Parameters({
		@Parameter(
			name = "MemberSeq",
			description = "Member Sequence",
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
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
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
	public ResponseEntity<BaseResponse> getMemberDetail(@Valid @RequestParam("memberSeq") Long memberSeq) {

		Member.GetMemberDetailResponse response = getMemberDetailUseCase.invoke(memberSeq);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@PutMapping(value = "/modify", name = "회원 정보 변경")
	@Operation(summary = "Modify User Info API", description = "Modify user Info API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
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
			responseCode = "UID001",
			description = "409 USER_ID_DUPLICATION_EXCEPTION",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> ModifyUserInfo(@RequestPart(value = "memberSeq") Long memberSeq,
		@RequestPart(value = "images", required = false) MultipartFile multipartFile,
		@RequestPart(value = "newUserId", required = false) String newUserId,
		@RequestPart(value = "newUserName", required = false) String newUserName) throws Exception {

		modifyUserInfoUseCase.invoke(memberSeq, multipartFile, newUserId, newUserName);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@GetMapping(value = "/info", name = "회원 상세 정보 By UserId")
	@Operation(summary = "Get Member Details By UserId API", description = "Get Member Details By UserId API")
	@Parameters({
		@Parameter(
			name = "userId",
			description = "uesrId",
			example = "dlswns11")
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
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
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
			responseCode = "SSB014",
			description = "400 SELF_SEARCH_BAD_REQUEST_EXCEPTION",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> getMemberDetailByUserId(@Valid @RequestParam("userId") String userId,
		HttpServletRequest request) {

		Member.GetMemberDetailByUserIdResponse response = getMemberDetailByUserIdUseCase.invoke(userId, request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@PostMapping(value = "/logout", name = "로그아웃")
	@Operation(summary = "Logout API", description = "Logout API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = BaseResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR",
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
	public ResponseEntity<BaseResponse> logout(@Valid @RequestBody Member.LogoutRequest request) {

		logoutUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

}
