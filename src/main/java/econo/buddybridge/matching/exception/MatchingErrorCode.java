package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MatchingErrorCode implements ErrorCode {
    MATCHING_NOT_FOUND("MA001", HttpStatus.NOT_FOUND, "존재하지 않는 매칭입니다."),
    MATCHING_UNAUTHORIZED_ACCESS("MA002", HttpStatus.FORBIDDEN, "사용자가 생성한 매칭방이 아닙니다."),
    EXCEEDED_MATCHING_LIMIT("MA003", HttpStatus.BAD_REQUEST, "모집이 완료되었습니다.")
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
