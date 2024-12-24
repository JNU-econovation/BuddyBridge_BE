package econo.buddybridge.websocket;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum WebSocketErrorCode implements ErrorCode {
    WS_INTERNAL_SERVER_ERROR("WS001", HttpStatus.INTERNAL_SERVER_ERROR, "채팅 기능에 일시적인 문제가 발생했습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    WebSocketErrorCode(String code, HttpStatus httpStatus, String message) {
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
