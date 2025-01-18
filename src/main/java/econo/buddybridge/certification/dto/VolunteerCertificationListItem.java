package econo.buddybridge.certification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDateTime;

public record VolunteerCertificationListItem(
        Long certificationId,

        String volunteerName,

        String volunteerEmail,

        Long postId,

        PostType postType,

        boolean isCertified,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime certificationCreatedDate,
        
        Boolean isBlackListed
) {

    @QueryProjection
    public VolunteerCertificationListItem {
    }
}
