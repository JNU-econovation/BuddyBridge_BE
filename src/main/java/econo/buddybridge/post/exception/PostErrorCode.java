package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND("P001", HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    POST_DELETE_NOT_ALLOWED("P002", HttpStatus.FORBIDDEN, "본인의 게시글만 삭제할 수 있습니다."),
    POST_UPDATE_NOT_ALLOWED("P003", HttpStatus.FORBIDDEN, "본인의 게시글만 수정할 수 있습니다."),
    POST_UNAUTHORIZED_ACCESS("P004", HttpStatus.BAD_REQUEST, "회원님이 작성한 게시글이 아닙니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    PostErrorCode(String code, HttpStatus httpStatus, String message) {
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
