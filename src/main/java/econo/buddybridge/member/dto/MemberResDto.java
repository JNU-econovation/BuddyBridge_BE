package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import lombok.Builder;

@Builder
public record MemberResDto(
        Long memberId,
        String name,
        String nickname,
        String profileImageUrl,
        String email,
        Integer age,
        Gender gender,
        DisabilityType disabilityType
) {
}