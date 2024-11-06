package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements ErrorCode {
    INVALID_TOKEN("JW001", HttpStatus.UNAUTHORIZED, "올바른 토큰이 아닙니다."),
    EXPIRED_TOKEN("JW002", HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    MISSING_TOKEN("JW003", HttpStatus.UNAUTHORIZED, "요청에 토큰이 포함되어있지 않습니다."),
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
