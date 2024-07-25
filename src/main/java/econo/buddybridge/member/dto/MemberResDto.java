package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;
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

    public MemberResDto(Member member) {
        this(
                member.getId(),
                member.getName(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getEmail(),
                member.getAge(),
                member.getGender(),
                member.getDisabilityType()
        );
    }
}