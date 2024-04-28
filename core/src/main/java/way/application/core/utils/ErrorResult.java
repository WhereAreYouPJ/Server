package way.application.core.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {
    // USER
    NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "Not Found Exception"),

    // SEASON
    BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "Bad Request Exception"),

    // SERVER
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
