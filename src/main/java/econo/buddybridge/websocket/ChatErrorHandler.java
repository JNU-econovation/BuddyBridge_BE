package econo.buddybridge.websocket;

import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class ChatErrorHandler extends StompSubProtocolErrorHandler {

    public ChatErrorHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        log.error("Exception type(예외 타입): {}", ex.getClass().getName());
        if (ex.getCause() != null) {
            log.error("Cause type(커스텀 예외 타입): {}", ex.getCause().getClass().getName());
        }
        log.error("Original error message(원본 에러 메시지): {}", ex.getMessage());

        ErrorCode errorCode = getErrorCode(ex);

        StompHeaderAccessor errorAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
        errorAccessor.setMessage(errorCode.getMessage());
        errorAccessor.setNativeHeader("code", errorCode.getCode());
        errorAccessor.setNativeHeader("status", String.valueOf(errorCode.getHttpStatus().value()));
        errorAccessor.setNativeHeader("messageType", MessageType.ERROR.getMessageType());

        return MessageBuilder.createMessage(
                errorCode.getMessage().getBytes(StandardCharsets.UTF_8),
                errorAccessor.getMessageHeaders()
        );
    }

    private ErrorCode getErrorCode(Throwable ex) {
        Throwable current = ex;

        while (current != null) {
            if (current instanceof BusinessException businessException) {
                log.error("BusinessException Error(커스텀 비즈니스 예외): {}", businessException.getErrorCode().getMessage());
                return businessException.getErrorCode();
            }
            current = current.getCause();
        }

        log.error("WebSocket Error : {}", WebSocketErrorCode.WS_INTERNAL_SERVER_ERROR.getMessage());
        return WebSocketErrorCode.WS_INTERNAL_SERVER_ERROR;
    }
}
