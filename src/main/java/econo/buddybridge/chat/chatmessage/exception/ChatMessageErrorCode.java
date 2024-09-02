package econo.buddybridge.chat.chatmessage.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ChatMessageErrorCode implements ErrorCode {
    LAST_CHAT_MESSAGE_NOT_FOUND("CH01", HttpStatus.NOT_FOUND, "마지막 메시지가 존재하지 않습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ChatMessageErrorCode(String code, HttpStatus httpStatus, String message) {
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
