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
import way.application.domain.location.Location;
import way.application.domain.location.usecase.DeleteLocationUseCase;
import way.application.domain.location.usecase.GetLocationUseCase;
import way.application.domain.location.usecase.SaveLocationUseCase;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/location", name = "장소")
@RequiredArgsConstructor
@Tag(name = "Location API", description = "업무 담당: 박종훈")
@OpenAPIDefinition(servers = {@Server(url = "/", description = "https://wlrmadjel.com")})
public class LocationController {
	private final SaveLocationUseCase saveLocationUseCase;
	private final DeleteLocationUseCase deleteLocationUseCase;
	private final GetLocationUseCase getLocationUseCase;

	@PostMapping(name = "장소 즐겨찾기")
	@Operation(summary = "장소 즐겨찾기 저장 API", description = "장소 즐겨찾기 저장 API")
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
			responseCode = "SLOTB005",
			description = "400 SAVED_LOCATION_OVER_TEN_BAD_EXCEPTION / 저장 개수 10개 초과 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse<Location.SaveLocationResponse>> save(Location.SaveLocationRequest request) {
		Location.SaveLocationResponse response = saveLocationUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}

	@DeleteMapping(name = "장소 즐겨찾기 삭제")
	@Operation(summary = "장소 즐겨찾기 삭제 API", description = "장소 즐겨찾기 삭제 API")
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
			responseCode = "LSB006",
			description = "400 LOCATION_SEQ_BAD_REQUEST_EXCEPTION / LOCATION_SEQ 오류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> delete(Location.DeleteLocationRequest request) {
		deleteLocationUseCase.invoke(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}

	@GetMapping(name = "장소 즐겨찾기 전체 검색")
	@Operation(summary = "장소 즐겨찾기 전체 검색 API", description = "장소 즐겨찾기 전체 검색 API")
	@Parameters({
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
	public ResponseEntity<BaseResponse<List<Location.GetLocationResponse>>> get(
		@Valid
		@RequestParam(name = "memberSeq") Long memberSeq) {
		List<Location.GetLocationResponse> response = getLocationUseCase.invoke(memberSeq);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(response));
	}
}
