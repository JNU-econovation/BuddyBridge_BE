package econo.buddybridge.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.exception.ErrorCode;
import econo.buddybridge.websocket.dto.WebSocketErrorResponseDto;
import econo.buddybridge.websocket.exception.WebSocketErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatErrorHandler extends StompSubProtocolErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        try {
            ErrorCode errorCode = getErrorCode(ex);
            WebSocketErrorResponseDto errorResponseDto = toErrorResponse(errorCode);

            return buildErrorMessage(errorResponseDto);
        } catch (JsonProcessingException e) {
            return super.handleClientMessageProcessingError(clientMessage, ex);
        }
    }

    private ErrorCode getErrorCode(Throwable ex) {
        Throwable current = ex;
        log.error("STOMP 예외 발생: {}", ex.getMessage());

        while (current != null) {
            if (current instanceof BusinessException businessException) {
                return businessException.getErrorCode();
            }
            current = current.getCause();
        }

        return WebSocketErrorCode.WS_INTERNAL_SERVER_ERROR;
    }

    private static WebSocketErrorResponseDto toErrorResponse(ErrorCode errorCode) {
        return WebSocketErrorResponseDto.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .httpStatus(errorCode.getHttpStatus())
                .messageType(MessageType.ERROR)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Message<byte[]> buildErrorMessage(WebSocketErrorResponseDto errorResponseDto) throws JsonProcessingException {
        StompHeaderAccessor errorAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
        errorAccessor.setContentType(MimeTypeUtils.APPLICATION_JSON);
        String jsonError = objectMapper.writeValueAsString(errorResponseDto);

        return MessageBuilder.createMessage(
                jsonError.getBytes(StandardCharsets.UTF_8),
                errorAccessor.getMessageHeaders()
        );
    }
}
