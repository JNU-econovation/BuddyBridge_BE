package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReportErrorCode implements ErrorCode {
    REPORT_NOT_FOUND("R001", HttpStatus.NOT_FOUND, "신고를 찾을 수 없습니다."),
    REPORT_POST_ALREADY_EXISTS("R002", HttpStatus.BAD_REQUEST, "이미 신고한 게시글입니다."),
    REPORT_COMMENT_ALREADY_EXISTS("R003", HttpStatus.BAD_REQUEST, "이미 신고한 댓글입니다."),
    REPORT_MATCHING_ALREADY_EXISTS("R004", HttpStatus.BAD_REQUEST, "이미 신고한 매칭입니다."),
    REPORT_INVALID_TYPE("R005", HttpStatus.BAD_REQUEST, "유효하지 않은 신고 유형입니다."),
    REPORT_SELF_POST("R006", HttpStatus.BAD_REQUEST, "자신의 게시글을 신고할 수 없습니다."),
    REPORT_SELF_COMMENT("R007", HttpStatus.BAD_REQUEST, "자신의 댓글을 신고할 수 없습니다."),
    REPORT_UNEXPECTED_CONVERT("R008", HttpStatus.INTERNAL_SERVER_ERROR, "신고 정보 변환 중 예기치 않은 오류가 발생했습니다.")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ReportErrorCode(String code, HttpStatus httpStatus, String message) {
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
