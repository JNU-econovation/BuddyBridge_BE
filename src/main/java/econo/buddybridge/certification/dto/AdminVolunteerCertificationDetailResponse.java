package econo.buddybridge.certification.dto;

import com.querydsl.core.annotations.QueryProjection;

public record AdminVolunteerCertificationDetailResponse(
        AdminCertificationAuthorDetailResponse authorDetail,
        AdminCertificationPostDetailResponse postDetail,
        AdminCertificationDetailResponse volunteeringDetail
) {

    @QueryProjection
    public AdminVolunteerCertificationDetailResponse {
    }
}
