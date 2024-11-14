package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.Gender;

import java.time.LocalDate;

public record MemberSignUpReqDto(
        String name,
        Gender gender,
        LocalDate birthDate,
        String email,
        String password
) {

}
