package way.application.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import way.application.core.utils.ErrorResult;

@Getter
@RequiredArgsConstructor
public class InvalidEmailException extends RuntimeException {
    private final ErrorResult errorResult;
}
