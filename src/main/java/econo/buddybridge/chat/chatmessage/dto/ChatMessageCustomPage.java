package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import java.util.List;
import lombok.Builder;

@Builder
public record ChatMessageCustomPage(
        PostType postType,
        Long postId,
        Long postAuthorId,
        ReceiverDto receiver,
        MatchingStatus matchingStatus,
        Boolean canVerificationRequest,
        List<ChatMessageResDto> chatMessages,
        Long cursor,
        Boolean nextPage
) {

    public static ChatMessageCustomPage of(Post post, Matching matching, ReceiverDto receiver, List<ChatMessageResDto> chatMessages, Long cursor, Boolean nextPage) {
        return ChatMessageCustomPage.builder()
                .postType(post.getPostType())
                .postId(post.getId())
                .postAuthorId(post.getAuthor().getId())
                .receiver(receiver)
                .matchingStatus(matching.getMatchingStatus())
                .canVerificationRequest(matching.canRequestCertification())
                .chatMessages(chatMessages)
                .cursor(cursor)
                .nextPage(nextPage)
                .build();
    }
}
