package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import lombok.Builder;

@Builder
public record PostAuthorDto(
        Long memberId,
        String nickname,
        String profileImageUrl,
        Integer age,
        Gender gender,
        DisabilityType disabilityType
) {

}
