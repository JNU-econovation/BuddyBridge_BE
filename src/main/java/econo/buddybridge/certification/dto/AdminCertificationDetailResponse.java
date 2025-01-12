package econo.buddybridge.certification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AdminCertificationDetailResponse(
        Long certificationId,

        String volunteerNickname,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime certificationCreatedDate,

        String volunteerName,

        String volunteerEmail,

        Long postId,

        PostType postType,

        LocalDate volunteerDate,

        AssistanceType assistanceType,

        LocalTime startTime,

        LocalTime endTime,

        String volunteerContent,

        boolean isCertified
) {

    @QueryProjection
    public AdminCertificationDetailResponse {
    }
}
