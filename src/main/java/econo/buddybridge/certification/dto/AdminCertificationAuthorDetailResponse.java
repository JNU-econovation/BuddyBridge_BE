package econo.buddybridge.certification.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;

public record AdminCertificationAuthorDetailResponse(
        String authorName,

        String authorNickname,

        Gender authorGender,

        Integer authorAge,

        DisabilityType authorDisabilityType
) {

}
