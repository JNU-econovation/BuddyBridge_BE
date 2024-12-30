package econo.buddybridge.chat.chatmessage.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatMessageResDto(
        Long messageId,
        Long senderId,
        String content,
        MessageType messageType,
        LocalDateTime createdAt
) {

    @QueryProjection
    public ChatMessageResDto {
    }

    public static ChatMessageResDto of(Long messageId, Long senderId, String content, MessageType messageType, LocalDateTime createdAt) {
        return ChatMessageResDto.builder()
                .messageId(messageId)
                .senderId(senderId)
                .content(content)
                .messageType(messageType)
                .createdAt(createdAt)
                .build();
    }
}
