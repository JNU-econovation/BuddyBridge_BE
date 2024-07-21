package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

import java.util.List;

@Builder
public record ChatMessageCustomPage(
        PostType postType,
        Long postId,
        ReceiverDto receiver,
        List<ChatMessageResDto> chatMessages,
        Long cursor,
        Boolean nextPage
) {
}
