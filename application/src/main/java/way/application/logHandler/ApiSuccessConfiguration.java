package way.application.logHandler;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import way.application.domain.ApiSuccessLogs;
import way.application.domain.ApiSuccessLogsJpaRepository;

@Component
@Aspect
@RequiredArgsConstructor
public class ApiSuccessConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(ApiSuccessConfiguration.class);
	private final ApiSuccessLogsJpaRepository logsRepository;

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Pointcut("within(way.application.controller..*)")
	public void onRequest() {
	}

	@Around("way.application.logHandler.ApiSuccessConfiguration.onRequest()")
	public Object requestLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		String serverIp = getServerIp().orElse("Unknown");

		try {
			request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
			CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(response);

			long start = System.currentTimeMillis();
			Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
			long end = System.currentTimeMillis();
			saveSuccessLog(request, responseWrapper, serverIp, start, end);
			return result;
		} catch (IllegalStateException e) {
			return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
		}
	}

	private void saveSuccessLog(HttpServletRequest request, CustomHttpServletResponseWrapper responseWrapper,
		String serverIp, long start, long end) {
		ApiSuccessLogs log = ApiSuccessLogs.builder()
			.serverIp(serverIp)
			.requestURL(request.getRequestURL().toString())
			.requestMethod(request.getMethod())
			.responseStatus(responseWrapper.getStatus())
			.clientIp(request.getRemoteAddr())
			.requestTime(LocalDateTime.now())
			.responseTime(LocalDateTime.now().plusNanos(end - start))
			.connectionTime(end - start)
			.build();

		logsRepository.save(log);
	}

	private Optional<String> getServerIp() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
						return Optional.of(inetAddress.getHostAddress());
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error while getting server IP address", e);
		}
		return Optional.empty();
	}
}
