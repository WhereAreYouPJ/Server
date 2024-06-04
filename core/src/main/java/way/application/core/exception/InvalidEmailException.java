package way.application.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import way.application.core.utils.ErrorResult;

// TODO: STATUS CODE 별 Exception 클래스 관리해야합니다.
@Getter
@RequiredArgsConstructor
public class InvalidEmailException extends RuntimeException {
	private final ErrorResult errorResult;
}
