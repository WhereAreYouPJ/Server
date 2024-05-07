package way.application.core.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

    // User Bad UB001
    USER_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "User Bad Request Exception", "UB002"),

    // Schedule Member Bad B002
    INVITED_MEMBER_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "Invited Member Bad Request Exception", "IMB003"),

    // SERVER
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception", "S500"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
