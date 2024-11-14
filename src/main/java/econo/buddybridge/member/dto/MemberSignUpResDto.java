package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;

public record MemberSignUpResDto(
        Long memberId,
        String name,
        String email,
        Integer age,
        Gender gender
) {

    public MemberSignUpResDto(Member member) {
        this(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getAge(),
                member.getGender()
        );
    }
}
