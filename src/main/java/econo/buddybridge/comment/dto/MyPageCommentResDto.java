package econo.buddybridge.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

import java.time.LocalDateTime;

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
