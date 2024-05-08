package way.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import way.application.core.base.BaseResponse;

@RestController
public class HealthCheckController {
    @GetMapping("/actuator/health")
    public ResponseEntity<BaseResponse> healthCheck() {
        return ResponseEntity.ok().body(BaseResponse.ofSuccess("SUCCESS"));
    }
}
