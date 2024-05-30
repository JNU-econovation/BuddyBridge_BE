package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import lombok.Builder;

@Builder
public record MemberDto (
        String name,
        String nickname,
        String profileImageUrl,
        String email,
        Integer age
//        String phone,
//        DisabilityType disabilityType,
//        Gender gender
) {
}