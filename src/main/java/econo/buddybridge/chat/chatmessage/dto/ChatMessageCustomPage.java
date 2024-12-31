package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.post.entity.PostType;
import java.util.List;
import lombok.Builder;

@Builder
public record ChatMessageCustomPage(
        PostType postType,
        Long postId,
        Long postAuthorId,
        ReceiverDto receiver,
        List<ChatMessageResDto> chatMessages,
        Long cursor,
        Boolean nextPage
) {

}
