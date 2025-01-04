package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MatchingStateErrorCode implements ErrorCode {
    ONLY_PENDING_COMPLETED_FAILED_TRANSITIONS_ALLOWED("MS001", HttpStatus.BAD_REQUEST, "매칭 중, 봉사를 받았어요(봉사 완료), 봉사를 받지 못했어요(매칭 실패) 상태로만 전환 가능합니다."),
    ONLY_DONE_STATE_TRANSITION_ALLOWED("MS002", HttpStatus.BAD_REQUEST, "매칭 완료 상태로 만 전환 가능합니다."),
    GIVER_CANNOT_TRANSITION_TO_FAILED("MS003", HttpStatus.BAD_REQUEST, "봉사자(GIVER)는 실패 상태로 전환할 수 없습니다."),
    GIVER_CANNOT_TRANSITION_TO_VOLUNTEERING_COMPLETED("MS004", HttpStatus.BAD_REQUEST, "봉사자(GIVER)는 봉사 완료 상태로 전환할 수 없습니다."),
    ONLY_GIVER_CAN_TRANSITION_TO_VOLUNTEERING_VERIFIED("MS005", HttpStatus.BAD_REQUEST, "봉사자(GIVER)만 봉사 인증 폼 작성이 가능하며, 다른 상태 변경 요청은 불가능합니다."),
    FAILED_MATCHING_STATE_CHANGE_FORBIDDEN("MS006", HttpStatus.BAD_REQUEST, "한 번 취소된 매칭은 상태 변경이 불가능합니다."),
    VERIFIED_MATCHING_STATE_CHANGE_FORBIDDEN("MS007", HttpStatus.BAD_REQUEST, "봉사 인증 완료된 매칭은 상태 변경이 불가능합니다."),
    MATCHING_STATUS_CHANGE_EVENT_INVALID_TYPE("MS008", HttpStatus.BAD_REQUEST, "유효하지 않은 매칭 상태 변경 유형입니다."),
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
