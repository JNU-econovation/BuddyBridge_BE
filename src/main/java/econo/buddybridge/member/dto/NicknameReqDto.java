package econo.buddybridge.member.dto;

import jakarta.validation.constraints.NotBlank;

public record NicknameReqDto(
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname
) {
}
