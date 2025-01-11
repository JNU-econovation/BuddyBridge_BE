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

        // 봉사 시간 부여 여부 -> 추후 계산해서 넣어주기

        @JsonFormat(pattern = "yyyy.MM.dd")
        LocalDateTime certificationCreatedDate
) {

    @QueryProjection
    public VolunteerCertificationListItem {
    }
}
