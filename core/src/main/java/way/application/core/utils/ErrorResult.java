package way.application.core.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

    // BAD_REQUEST
    USER_ID_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "USER_ID_BAD_REQUEST_EXCEPTION", "UIB002"),
    SCHEDULE_ID_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "SCHEDULE_ID_BAD_REQUEST_EXCEPTION", "SIB003"),

    // SERVER
    FIREBASE_CLOUD_MESSAGING_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "FIREBASE_CLOUD_MESSAGING_EXCEPTION", "S501"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception", "S500"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
