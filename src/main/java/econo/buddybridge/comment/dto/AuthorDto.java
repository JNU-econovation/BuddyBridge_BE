package econo.buddybridge.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record AuthorDto(
        Long memberId,
        String nickname,
        String profileImg
) {
    @QueryProjection
    public AuthorDto(Long memberId, String nickname, String profileImg) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
