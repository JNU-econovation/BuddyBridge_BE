package econo.buddybridge.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.ScheduleType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record PostListItemDto(
        Long id,
        String title,
        AssistanceType assistanceType,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType,
        District district,
        PostType postType,
        PostStatus postStatus,
        DisabilityType disabilityType,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceStartTime,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceEndTime,
        Boolean isLiked
) {

    public PostListItemDto(Post post, Boolean isLiked) {
        this(
                post.getId(),
                post.getTitle(),
                post.getAssistanceType(),
                post.getSchedule().getStartDate(),
                post.getSchedule().getEndDate(),
                post.getSchedule().getScheduleType(),
                post.getDistrict(),
                post.getPostType(),
                post.getPostStatus(),
                post.getDisabilityType(),
                post.getAssistanceTime().getAssistanceStartTime(),
                post.getAssistanceTime().getAssistanceEndTime(),
                isLiked
        );
    }
}
