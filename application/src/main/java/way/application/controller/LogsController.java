package way.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import way.application.core.errorLogs.ApiErrorLogs;
import way.application.core.errorLogs.ApiErrorLogsJpaRepository;
import way.application.data.member.MemberEntity;
import way.application.data.member.MemberJpaRepository;
import way.application.domain.ApiSuccessLogs;
import way.application.domain.ApiSuccessLogsJpaRepository;

@Controller
@RequestMapping(value = "/admin", name = "로그 Controller입니다. 사용 X")
@RequiredArgsConstructor
@Tag(name = "Logs API", description = "업무 담당: 박종훈")
@OpenAPIDefinition(servers = {@Server(url = "/", description = "https://wlrmadjel.com")})
public class LogsController {
	private final MemberJpaRepository memberJpaRepository;
	private final ApiSuccessLogsJpaRepository apiSuccessLogsJpaRepository;
	private final ApiErrorLogsJpaRepository apiErrorLogsJpaRepository;

	@GetMapping()
	public String getDashboard(Model model) {
		List<MemberEntity> users = memberJpaRepository.findAll();
		model.addAttribute("users", users);

		List<ApiSuccessLogs> successLogs = apiSuccessLogsJpaRepository.findAll().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("successLogs", successLogs);

		List<ApiErrorLogs> errorLogs = apiErrorLogsJpaRepository.findAll().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("errorLogs", errorLogs);

		return "admin/dashboard";
	}

	@GetMapping("/all-logs")
	public String allLogs(Model model) {
		List<MemberEntity> users = memberJpaRepository.findAll();
		model.addAttribute("users", users);

		List<ApiSuccessLogs> successLogs = apiSuccessLogsJpaRepository.findAll().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("successLogs", successLogs);

		List<ApiErrorLogs> errorLogs = apiErrorLogsJpaRepository.findAll().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("errorLogs", errorLogs);

		return "admin/all-logs";
	}

	@GetMapping("/users")
	public String getAllUsersLogs(Model model) {
		List<MemberEntity> users = memberJpaRepository.findAll();
		model.addAttribute("users", users);
		return "admin/users";
	}

	@GetMapping("/success-logs")
	public String getAllSuccessLogs(Model model) {
		List<ApiSuccessLogs> successLogs = apiSuccessLogsJpaRepository.findAll().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());

		model.addAttribute("successLogs", successLogs);
		return "admin/success-logs";
	}

	@GetMapping("/error-logs")
	public String getAllFailLogs(Model model) {
		List<ApiErrorLogs> errorLogs = apiErrorLogsJpaRepository.findAll().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());

		model.addAttribute("errorLogs", errorLogs);
		return "admin/error-logs";
	}
}
