package econo.buddybridge.member.dto;

import econo.buddybridge.common.validation.EnumTypeValue;
import econo.buddybridge.member.entity.DisabilityType;
import jakarta.validation.constraints.NotBlank;

public record MemberReqDto(
        @NotBlank(message = "변경할 닉네임은 필수입니다.")
        String nickname,

        @EnumTypeValue(enumClass = DisabilityType.class)
        String disabilityType
) {

}
