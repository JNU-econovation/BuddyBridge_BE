package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.chat.chatmessage.entity.MessageType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageResDto(
        Long messageId,
        Long senderId,
        String content,
        MessageType messageType,
        LocalDateTime createdAt
) {
}
