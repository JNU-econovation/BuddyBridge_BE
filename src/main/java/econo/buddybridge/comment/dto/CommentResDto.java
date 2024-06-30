package econo.buddybridge.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentResDto(
        Long commentId,
        Long postId,
        AuthorDto author,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    @QueryProjection
    public CommentResDto(Long commentId, Long postId, AuthorDto author, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
