package econo.buddybridge.certification.dto.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record VolunteeringDetailResponse(
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

        String volunteerContent
) {

}
