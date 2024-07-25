package econo.buddybridge.post.dto;

import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.*;
import lombok.Builder;
import econo.buddybridge.post.service.PostService;

import java.time.LocalDateTime;

@Builder
public record PostResDto(
        Long id,
        MemberResDto author,
        String title,
        AssistanceType assistanceType,
        LocalDateTime startTime,
        LocalDateTime endTime,
        ScheduleType scheduleType,
        String scheduleDetails,
        District district,
        String content,
        PostType postType,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        PostStatus postStatus,
        DisabilityType disabilityType
) {

    public PostResDto(Post post) {
        this(
                post.getId(),
                PostService.toMemberResDto(post.getAuthor()),
                post.getTitle(),
                post.getAssistanceType(),
                post.getSchedule().getStartTime(),
                post.getSchedule().getEndTime(),
                post.getSchedule().getScheduleType(),
                post.getSchedule().getScheduleDetails(),
                post.getDistrict(),
                post.getContent(),
                post.getPostType(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getPostStatus(),
                post.getDisabilityType()
        );
    }

}
