package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.Schedule;
import econo.buddybridge.post.entity.ScheduleType;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostReqDto(
        Long memberId,
        String title,
        AssistanceType assistanceType,
        LocalDateTime startTime,
        LocalDateTime endTime,
        ScheduleType scheduleType,
        String scheduleDetails,
        District district,
        String content,
        PostType postType,
        DisabilityType disabilityType
) {

    public Post toEntity(Member author) {
        return Post.builder()
                .author(author)
                .title(title)
                .assistanceType(assistanceType)
                .schedule(Schedule.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .scheduleType(scheduleType)
                        .scheduleDetails(scheduleDetails)
                        .build())
                .district(district)
                .content(content)
                .postType(postType)
                .postStatus(PostStatus.RECRUITING)
                .disabilityType(disabilityType)
                .build();
    }
}
