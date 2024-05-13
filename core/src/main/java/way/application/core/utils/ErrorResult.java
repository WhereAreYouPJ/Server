package way.application.core.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

    // BAD_REQUEST
    MEMBER_ID_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "MEMBER_ID_BAD_REQUEST_EXCEPTION", "MIB002"),
    SCHEDULE_ID_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "SCHEDULE_ID_BAD_REQUEST_EXCEPTION", "SIB003"),
    MEMBER_ID_NOT_IN_SCHEDULE_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "MEMBER_ID_NOT_IN_SCHEDULE_BAD_REQUEST_EXCEPTION", "MINISB004"),
    LOCATION_OVER_TEN_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "SAVED_LOCATION_OVER_TEN_BAD_EXCEPTION", "SLOTB005"),
    LOCATION_ID_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "LOCATION_ID_BAD_REQUEST", "LIB006"),
    LOCATION_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "LOCATION_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION", "LDCBMB007"),
    SCHEDULE_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "SCHEDULE_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION", "SDCBMB008"),

    // SERVER
    FIREBASE_CLOUD_MESSAGING_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "FIREBASE_CLOUD_MESSAGING_EXCEPTION", "S501"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception", "S500"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
