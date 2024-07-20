package econo.buddybridge.matching.dto;

import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MatchingResDto(
        Long matchingId,
        PostType postType,
        String lastMessage,
        LocalDateTime lastMessageTime,
        MessageType messageType,
        MatchingStatus matchingStatus,
        ReceiverDto receiverDto
) {

}
