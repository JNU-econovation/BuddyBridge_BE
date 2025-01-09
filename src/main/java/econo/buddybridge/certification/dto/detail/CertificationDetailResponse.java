package econo.buddybridge.certification.dto.detail;

public record CertificationDetailResponse(
        CertificationPostAuthorDetailResponse authorDetail,
        CertificationPostDetailResponse postDetail,
        VolunteeringDetailResponse volunteeringDetail
) {

}
