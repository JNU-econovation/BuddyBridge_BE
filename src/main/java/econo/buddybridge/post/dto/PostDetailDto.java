package econo.buddybridge.post.dto;

import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import lombok.Builder;

@Builder
public record PostDetailDto(
        MemberResDto author,
        PostDetailInfoDto post,
        Boolean isLiked
) {

    public PostDetailDto(Post post, Boolean isLiked, PostStatus postStatus) {
        this(
                new MemberResDto(post.getAuthor()),
                PostDetailInfoDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .schedule(
                                ScheduleDetailResDto.builder()
                                        .startDate(post.getSchedule().getStartDate())
                                        .endDate(post.getSchedule().getEndDate())
                                        .scheduleType(post.getSchedule().getScheduleType())
                                        .scheduleDetails(post.getSchedule().getScheduleDetails())
                                        .build()
                        )
                        .district(post.getDistrict())
                        .content(post.getContent())
                        .postType(post.getPostType())
                        .createdAt(post.getCreatedAt())
                        .assistance(
                                AssistanceResDto.builder()
                                        .assistanceType(post.getAssistanceType())
                                        .assistanceStartTime(post.getAssistanceTime().getAssistanceStartTime())
                                        .assistanceEndTime(post.getAssistanceTime().getAssistanceEndTime())
                                        .build()
                        )
                        .postStatus(postStatus)
                        .build(),
                isLiked
        );
    }
}
