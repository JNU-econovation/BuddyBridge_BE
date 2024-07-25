package econo.buddybridge.matching.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MatchingResDto(
        Long matchingId,
        PostType postType,
        Long postId,
        String lastMessage,
        LocalDateTime lastMessageTime,
        MessageType messageType,
        MatchingStatus matchingStatus,
        ReceiverDto receiver
) {
    @QueryProjection
    public MatchingResDto {
    }
}
