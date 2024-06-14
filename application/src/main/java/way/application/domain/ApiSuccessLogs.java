package way.application.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "API_SUCCESS_LOGS")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class ApiSuccessLogs {
	@Id
	@Column(name = "api_success_logs_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apiSuccessLogsSeq;

	@Column(name = "server_ip")
	private String serverIp;

	@Column(name = "request_url", length = 4096)
	private String requestURL;

	@Column(name = "request_method")
	private String requestMethod;

	@Column(name = "response_status")
	private Integer responseStatus;

	@Column(name = "client_ip")
	private String clientIp;

	@Column(name = "request_time")
	private LocalDateTime requestTime;

	@Column(name = "response_time")
	private LocalDateTime responseTime;

	@Column(name = "connection_time")
	private Long connectionTime;
}
