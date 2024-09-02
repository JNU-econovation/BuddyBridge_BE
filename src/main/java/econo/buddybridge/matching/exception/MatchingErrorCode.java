package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MatchingErrorCode implements ErrorCode {
    MATCHING_NOT_FOUND("MA001", HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    MATCHING_UNAUTHORIZED_ACCESS("MA002", HttpStatus.FORBIDDEN, "사용자가 생성한 매칭방이 아닙니다.")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    MatchingErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
