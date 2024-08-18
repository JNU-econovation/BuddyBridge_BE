package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND("C001", HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    COMMENT_UPDATE_NOT_ALLOWED("C002", HttpStatus.FORBIDDEN, "본인의 댓글만 수정할 수 있습니다."),
    COMMENT_DELETE_NOT_ALLOWED("C003", HttpStatus.FORBIDDEN, "본인의 댓글만 삭제할 수 있습니다."),
    COMMENT_INVALID_DIRECTION("C004", HttpStatus.BAD_REQUEST, "올바르지 않은 정렬 방식입니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    CommentErrorCode(String code, HttpStatus httpStatus, String message) {
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
