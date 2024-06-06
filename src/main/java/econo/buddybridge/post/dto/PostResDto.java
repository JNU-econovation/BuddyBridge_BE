package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResDto(
        Long id,
        Member author,
        String title,
        AssistanceType assistanceType,
        Schedule schedule,
        District district,
        String content,
        PostType postType,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        PostStatus postStatus
) {
    public PostResDto(Post post) {
        this(
            post.getId(),
            post.getAuthor(),
            post.getTitle(),
            post.getAssistanceType(),
            post.getSchedule(),
            post.getDistrict(),
            post.getContent(),
            post.getPostType(),
            post.getCreatedAt(),
            post.getModifiedAt(),
            post.getPostStatus()
        );
    }
}
