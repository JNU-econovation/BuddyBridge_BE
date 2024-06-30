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
    public AuthorDto {
    }
}
