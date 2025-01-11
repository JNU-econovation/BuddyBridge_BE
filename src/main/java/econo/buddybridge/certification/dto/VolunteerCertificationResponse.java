package econo.buddybridge.certification.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;
import java.time.LocalTime;

public record VolunteerCertificationResponse(
        Long certificationId,

        String volunteerName,

        String volunteerEmail,

        Long postId,

        PostType postType,

        LocalDate volunteerDate,

        AssistanceType assistanceType,

        LocalTime startTime,

        LocalTime endTime,

        String volunteerContent
) {

    @QueryProjection
    public VolunteerCertificationResponse {
    }
}
