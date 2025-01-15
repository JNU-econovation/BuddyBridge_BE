package econo.buddybridge.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDateTime;
import lombok.Builder;

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
        PostStatus postStatus,
        Boolean isLiked
) {

    @QueryProjection
    public PostDetailInfoDto {
    }
}
