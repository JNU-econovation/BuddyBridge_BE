package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements ErrorCode {
    INVALID_ACCESS_TOKEN("JW001", HttpStatus.UNAUTHORIZED, "올바른 ACCESS 토큰이 아닙니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    JwtErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
