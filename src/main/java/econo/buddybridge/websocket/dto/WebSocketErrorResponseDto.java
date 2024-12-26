package econo.buddybridge.websocket.dto;

import econo.buddybridge.chat.chatmessage.entity.MessageType;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record WebSocketErrorResponseDto(
        String code,
        String message,
        HttpStatus httpStatus,
        MessageType messageType,
        LocalDateTime createdAt
) {
}
