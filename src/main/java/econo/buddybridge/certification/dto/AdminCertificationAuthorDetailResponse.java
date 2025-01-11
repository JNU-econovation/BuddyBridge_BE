package econo.buddybridge.certification.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;

public record AdminCertificationAuthorDetailResponse(
        String authorName,

        String authorNickname,

        Gender authorGender,

        Integer authorAge,

        DisabilityType authorDisabilityType
) {

    @QueryProjection
    public AdminCertificationAuthorDetailResponse {
    }
}
