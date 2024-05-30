package econo.buddybridge.member.dto;

import lombok.Builder;

@Builder
public record MemberDto (
        String nickname,
        String email,
        Integer age,
        String profileImageUrl
) {
}