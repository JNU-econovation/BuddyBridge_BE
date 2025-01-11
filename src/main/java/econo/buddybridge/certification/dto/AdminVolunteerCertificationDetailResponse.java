package econo.buddybridge.certification.dto;

public record AdminVolunteerCertificationDetailResponse(
        AdminCertificationAuthorDetailResponse authorDetail,
        AdminCertificationPostDetailResponse postDetail,
        AdminCertificationDetailResponse volunteeringDetail
) {

}
