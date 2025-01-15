package econo.buddybridge.certification.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.dto.PostDetailDto;

public record AdminVolunteerCertificationDetailResponse(
        PostDetailDto post,
        AdminCertificationDetailResponse certification
) {

    @QueryProjection
    public AdminVolunteerCertificationDetailResponse {
    }
}
