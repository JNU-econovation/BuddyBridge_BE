package econo.buddybridge.post.dto;

import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

@Builder
public record CompletedVolunteerPostDto(
        Long postId,
        String title,
        PostType postType,
        PostStatus postStatus,
        District district,
        DisabilityType disabilityType,
        AssistanceType assistanceType,
        ScheduleDetailResDto schedule,
        MatchingStatus matchingStatus
) {

}
