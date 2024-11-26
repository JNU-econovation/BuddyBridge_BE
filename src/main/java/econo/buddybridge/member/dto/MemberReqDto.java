package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.DisabilityType;

public record MemberReqDto(
        String nickname,
        DisabilityType disabilityType
) {

}
