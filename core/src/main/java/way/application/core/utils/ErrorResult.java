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
    MEMBER_ID_NOT_IN_SCHEDULE_BAD_EXCEPTION(HttpStatus.BAD_REQUEST, "MEMBER_ID_NOT_IN_SCHEDULE_BAD_EXCEPTION", "MINISB004"),
    PASSWORD_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST,"PASSWORD_BAD_REQUEST_EXCEPTION","PB005"),

    // SERVER
    FIREBASE_CLOUD_MESSAGING_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "FIREBASE_CLOUD_MESSAGING_EXCEPTION", "S501"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception", "S500"),

    //conflict
    USER_ID_DUPLICATION_EXCEPTION(HttpStatus.CONFLICT, "USER_ID_DUPLICATION_EXCEPTION", "UID001"),
    EMAIL_DUPLICATION_EXCEPTION(HttpStatus.CONFLICT, "EMAIL_DUPLICATION_EXCEPTION", "ED002")
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
