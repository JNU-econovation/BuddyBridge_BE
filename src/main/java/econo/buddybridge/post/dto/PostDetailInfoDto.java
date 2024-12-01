package econo.buddybridge.post.dto;

import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDetailInfoDto(
        Long id,
        String title,
        ScheduleDetailResDto schedule,
        District district,
        String content,
        PostType postType,
        LocalDateTime createdAt,
        AssistanceResDto assistance,
        PostStatus postStatus
) {
}
