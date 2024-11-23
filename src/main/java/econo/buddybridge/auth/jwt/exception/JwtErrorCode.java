package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements ErrorCode {
    INVALID_ACCESS_TOKEN("JW001", HttpStatus.UNAUTHORIZED, "올바른 ACCESS 토큰이 아닙니다."),
    INVALID_REFRESH_TOKEN("JW002", HttpStatus.UNAUTHORIZED, "올바른 REFRESH 토큰이 아닙니다."),
    EXPIRED_TOKEN("JW003", HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    MISSING_TOKEN("JW004", HttpStatus.UNAUTHORIZED, "요청에 토큰이 포함되어있지 않습니다."),
    LOGGED_OUT_TOKEN("JW005", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다. 다시 로그인해주세요."),
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
