package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    SESSION_ALREADY_EXISTS("A001", HttpStatus.BAD_REQUEST, "이미 세션이 존재합니다. 삭제 응답 후 다시 시도해주세요."),
    FEIGN_REDIRECT("A002", HttpStatus.OK, "Feign Client Logout Redirect"),
    FEIGN_KAKAO_EXCEPTION("A003", HttpStatus.BAD_REQUEST, "카카오 API 호출 오류가 발생했습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(String code, HttpStatus httpStatus, String message) {
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
