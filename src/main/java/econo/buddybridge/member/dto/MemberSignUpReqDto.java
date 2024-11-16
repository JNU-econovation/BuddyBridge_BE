package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MemberSignUpReqDto(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,

        @NotNull(message = "성별을 선택해주세요.")
        Gender gender,

        @NotNull(message = "생년월일을 입력해주세요.")
        LocalDate birthDate,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {

}
