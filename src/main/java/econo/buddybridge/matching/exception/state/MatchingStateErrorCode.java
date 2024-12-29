package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MatchingStateErrorCode implements ErrorCode {
    INVALID_STATE_TRANSITION_TO_DONE("MS001", HttpStatus.BAD_REQUEST, "매칭 완료 상태로 전환할 수 없습니다."),
    INVALID_STATE_TRANSITION_TO_PENDING("MS002", HttpStatus.BAD_REQUEST, "매칭 대기 상태로 전환할 수 없습니다."),
    INVALID_STATE_TRANSITION_TO_FAILED("MS003", HttpStatus.BAD_REQUEST, "실패 상태로 전환할 수 없습니다."),
    INVALID_STATE_TRANSITION_TO_VOLUNTEERING_COMPLETED("MS004", HttpStatus.BAD_REQUEST, "봉사 완료 상태로 전환할 수 없습니다."),
    INVALID_STATE_TRANSITION_TO_VOLUNTEERING_VERIFIED("MS005", HttpStatus.BAD_REQUEST, "봉사 인증 완료 상태로 전환할 수 없습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    MatchingStateErrorCode(String code, HttpStatus httpStatus, String message) {
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
