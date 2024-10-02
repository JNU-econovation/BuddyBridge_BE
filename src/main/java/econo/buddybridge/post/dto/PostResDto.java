package econo.buddybridge.post.dto;

import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.ScheduleType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResDto(
        Long id,
        MemberResDto author,
        String title,
        AssistanceType assistanceType,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType,
        String scheduleDetails,
        District district,
        String content,
        PostType postType,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        PostStatus postStatus,
        DisabilityType disabilityType,
        Gender gender,
        Integer age,
        LocalDateTime assistanceStartTime,
        LocalDateTime assistanceEndTime,
        Integer headcount,
        Boolean isLiked
) {

    public PostResDto(Post post, Boolean isLiked) {
        this(
                post.getId(),
                new MemberResDto(post.getAuthor()),
                post.getTitle(),
                post.getAssistanceType(),
                post.getSchedule().getStartDate(),
                post.getSchedule().getEndDate(),
                post.getSchedule().getScheduleType(),
                post.getSchedule().getScheduleDetails(),
                post.getDistrict(),
                post.getContent(),
                post.getPostType(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getPostStatus(),
                post.getDisabilityType(),
                post.getGender(),
                post.getAge(),
                post.getAssistanceStartTime(),
                post.getAssistanceEndTime(),
                post.getHeadcount(),
                isLiked
        );
    }
}
