package econo.buddybridge.websocket;

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

        log.error("웹 소켓 에러 발생: {}", ex.getMessage());

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(clientMessage);
        StompCommand command = accessor.getCommand();

        if (ex instanceof BusinessException businessException) {
            return handleBusinessException(command, businessException);
        }

        return handleUnauthorizedException(command, ex);
    }

    private Message<byte[]> handleUnauthorizedException(StompCommand command, Throwable ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        // Todo : 하드코딩 제거

        log.error("Unauthorized 에러 발생: {}", ex.getMessage());

        accessor.setMessage("Unauthorized");
        accessor.setNativeHeader("code", "401");

        return MessageBuilder.createMessage(
                "Unauthorized".getBytes(StandardCharsets.UTF_8),
                accessor.getMessageHeaders()
        );
    }

    private Message<byte[]> handleBusinessException(StompCommand command, BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        log.error("비즈니스 에러 발생: {}", errorCode.getMessage());
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setMessage(errorCode.getMessage());
        accessor.setNativeHeader("code", errorCode.getCode());
        accessor.setNativeHeader("status", String.valueOf(errorCode.getHttpStatus().value()));

        return MessageBuilder.createMessage(
                errorCode.getMessage().getBytes(StandardCharsets.UTF_8),
                accessor.getMessageHeaders()
        );
    }
}
