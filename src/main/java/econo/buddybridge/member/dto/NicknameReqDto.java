package econo.buddybridge.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NicknameReqDto(
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]{2,10}$", message = "닉네임은 2-10자의 영문, 숫자, 한글, 공백만 입력 가능합니다.")
        String nickname
) {

}
