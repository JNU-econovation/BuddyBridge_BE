package econo.buddybridge.post.dto;

import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import lombok.Builder;

@Builder
public record PostListItemDto(
        PostListDto post,
        Boolean isLiked
) {

    public PostListItemDto(Post post, Boolean isLiked, PostStatus postStatus) {
        this(
                PostListDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .district(post.getDistrict())
                        .postType(post.getPostType())
                        .postStatus(postStatus)
                        .disabilityType(post.getDisabilityType())
                        .assistance(
                                AssistanceResDto.builder()
                                        .assistanceType(post.getAssistanceType())
                                        .assistanceStartTime(post.getAssistanceTime().getAssistanceStartTime())
                                        .assistanceEndTime(post.getAssistanceTime().getAssistanceEndTime())
                                        .build()
                        )
                        .schedule(
                                ScheduleListResDto.builder()
                                        .startDate(post.getSchedule().getStartDate())
                                        .endDate(post.getSchedule().getEndDate())
                                        .scheduleType(post.getSchedule().getScheduleType())
                                        .build()
                        )
                        .build(),
                isLiked
        );
    }
}
