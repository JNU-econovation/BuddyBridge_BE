package econo.buddybridge.blacklist.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum BlackListErrorCode implements ErrorCode {
    BLACK_LIST_ALREADY_EXISTS("BL001", HttpStatus.BAD_REQUEST, "이미 블랙리스트에 등록된 회원입니다."),
    BLACK_LIST_NOT_FOUND("BL002", HttpStatus.NOT_FOUND, "블랙리스트에 등록되지 않은 회원입니다."),
    BLACK_LIST_REQUEST_FORBIDDEN("BL003", HttpStatus.FORBIDDEN, "블랙리스트에 등록된 회원입니다. 서비스를 이용할 수 없습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    BlackListErrorCode(String code, HttpStatus httpStatus, String message) {
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
