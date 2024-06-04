package way.application.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import way.application.core.base.BaseResponse;

@RestController

@Tag(name = "Server Health Check", description = "서버 환경 확인을 위한 URL: https://wlrmadjel.com/actuator/health 반환값 200이면 Server OK")
@OpenAPIDefinition(servers = {@Server(url = "/", description = "https://wlrmadjel.com")})
public class HealthCheckController {
	@GetMapping("/actuator/health")
	public ResponseEntity<BaseResponse> healthCheck() {
		return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
	}
}
