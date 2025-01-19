package econo.buddybridge.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostType;
import lombok.Builder;

@Builder
public record CompletedVolunteerPostDto(
        Long postId,
        Long matchingId,
        String giverName,
        String giverEmail,
        String title,
        PostType postType,
        PostStatus postStatus,
        District district,
        DisabilityType disabilityType,
        AssistanceType assistanceType,
        CompletedVolunteerScheduleDto schedule,
        MatchingStatus matchingStatus,
        Boolean canVerificationRequest
) {

    @QueryProjection
    public CompletedVolunteerPostDto {
    }
}
