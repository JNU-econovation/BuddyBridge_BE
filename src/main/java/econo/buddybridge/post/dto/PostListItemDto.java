package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

@Builder
public record PostListItemDto(
        Long id,
        String title,
        District district,
        PostType postType,
        PostStatus postStatus,
        DisabilityType disabilityType,
        AssistanceResDto assistance,
        ScheduleListResDto schedule,
        Boolean isLiked
) {

}
