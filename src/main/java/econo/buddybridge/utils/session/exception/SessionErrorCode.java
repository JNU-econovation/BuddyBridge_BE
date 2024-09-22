package econo.buddybridge.utils.session.exception;

import econo.buddybridge.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SessionErrorCode implements ErrorCode {
    SESSION_NOT_FOUND("S001", HttpStatus.NOT_FOUND, "세션이 존재하지 않습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    SessionErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
