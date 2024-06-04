package way.application.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import way.application.core.utils.ErrorResult;

@Getter
@RequiredArgsConstructor
public class ConflictException extends RuntimeException {
	private final ErrorResult errorResult;
}
