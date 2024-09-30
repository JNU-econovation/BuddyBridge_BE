package econo.buddybridge.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MyPageCommentResDto(
        String content,
        Long commentId,
        Long postId,
        String postTitle,
        PostStatus postStatus,
        PostType postType,
        DisabilityType disabilityType,
        AssistanceType assistanceType,
        LocalDateTime postCreatedAt
) {

    @QueryProjection
    public MyPageCommentResDto {
    }
}
