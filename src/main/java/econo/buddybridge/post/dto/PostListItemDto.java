package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
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

    public PostListItemDto(Post post, Boolean isLiked, PostStatus postStatus) {
        this(
                post.getId(),
                post.getTitle(),
                post.getDistrict(),
                post.getPostType(),
                postStatus,
                post.getDisabilityType(),
                AssistanceResDto.builder()
                        .assistanceType(post.getAssistanceType())
                        .assistanceStartTime(post.getAssistanceTime().getAssistanceStartTime())
                        .assistanceEndTime(post.getAssistanceTime().getAssistanceEndTime())
                        .build(),
                ScheduleListResDto.builder()
                        .startDate(post.getSchedule().getStartDate())
                        .endDate(post.getSchedule().getEndDate())
                        .scheduleType(post.getSchedule().getScheduleType())
                        .build(),
                isLiked
        );
    }
}
