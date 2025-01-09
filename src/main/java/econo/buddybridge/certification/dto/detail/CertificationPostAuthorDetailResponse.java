package econo.buddybridge.certification.dto.detail;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;

public record CertificationPostAuthorDetailResponse(
        String authorName,

        String authorNickname,

        Gender authorGender,

        Integer authorAge,

        DisabilityType authorDisabilityType
) {

}
