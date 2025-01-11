package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MatchingErrorCode implements ErrorCode {
    MATCHING_NOT_FOUND("MA001", HttpStatus.NOT_FOUND, "존재하지 않는 매칭입니다."),
    MATCHING_UNAUTHORIZED_ACCESS("MA002", HttpStatus.FORBIDDEN, "사용자가 생성한 매칭방이 아닙니다."),
    MATCHING_COMPLETED("MA003", HttpStatus.BAD_REQUEST, "모집이 완료되었습니다."),
    MATCHING_NOT_PARTICIPANT("MA004", HttpStatus.FORBIDDEN, "매칭에 참여한 사용자가 아닙니다."),
    MATCHING_ALREADY_EXISTS("MA005", HttpStatus.BAD_REQUEST, "이미 생성된 매칭이 존재합니다."),
    COMMENT_NOT_BELONG_TO_MATCHING("MA006", HttpStatus.BAD_REQUEST, "해당 댓글이 게시글에 속하지 않습니다."),
    MATCHING_STATUS_NOT_DONE("MA007", HttpStatus.BAD_REQUEST, "매칭이 완료되지 않았습니다."),
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
