package econo.buddybridge.member.dto;

import econo.buddybridge.member.entity.Gender;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record MemberSignUpReqDto(
        @NotBlank(message = "이름을 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z가-힣\\s]{2,18}$", message = "이름은 2-18자의 영문, 한글, 공백만 입력 가능합니다.")
        String name,

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]{2,10}$", message = "닉네임은 2-10자의 영문, 숫자, 한글, 공백만 입력 가능합니다.")
        String nickname,

        @NotNull(message = "성별을 선택해주세요.")
        Gender gender,

        @NotNull(message = "생년월일을 입력해주세요.")
        LocalDate birthDate,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])(?=\\S+$).{8,16}$", message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 16자 이하로 입력해주세요.")
        String password
) {

    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 100;

    @AssertTrue(message = "만 16세 이상 100세 이하만 가능합니다.")
    private boolean isValidAge() {
        LocalDate now = LocalDate.now();
        LocalDate maxBirthDate = now.minusYears(MIN_AGE);
        LocalDate minBirthDate = now.minusYears(MAX_AGE);
        return birthDate.isBefore(maxBirthDate) && !birthDate.isBefore(minBirthDate);
    }
}
