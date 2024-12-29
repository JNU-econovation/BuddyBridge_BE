package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MatchingStateErrorCode implements ErrorCode {
    INVALID_STATE_TRANSITION("MS001", HttpStatus.BAD_REQUEST, "해당 상태로 전환이 불가능 합니다."),
    INVALID_STATE_TRANSITION_TO_DONE("MS002", HttpStatus.BAD_REQUEST, "매칭 완료 상태로 전환할 수 없습니다."),
    INVALID_STATE_TRANSITION_TO_FAILED("MS003", HttpStatus.BAD_REQUEST, "실패 상태로 전환할 수 없습니다."),
    INVALID_STATE_TRANSITION_TO_VOLUNTEERING_COMPLETED("MS004", HttpStatus.BAD_REQUEST, "봉사 완료 상태로 전환할 수 없습니다."),
    INVALID_STATE_TRANSITION_TO_VOLUNTEERING_VERIFIED("MS005", HttpStatus.BAD_REQUEST, "봉사 인증 완료 요청 혹은 봉사자(GIVER)가 인증 완료 상태로 변경한 건지 확인해주세요."),
    INVALID_STATE_TRANSITION_FOR_FAILED("MS006", HttpStatus.BAD_REQUEST, "한 번 취소된 매칭은 상태 변경이 불가능합니다."),
    INVALID_STATE_TRANSITION_FOR_VOLUNTEERING_VERIFIED("MS007", HttpStatus.BAD_REQUEST, "봉사 인증 완료된 매칭은 상태 변경이 불가능합니다."),
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
