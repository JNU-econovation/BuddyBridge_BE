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
}
