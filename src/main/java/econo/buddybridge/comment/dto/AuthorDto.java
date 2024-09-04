package econo.buddybridge.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.member.entity.Gender;
import lombok.Builder;

@Builder
public record AuthorDto(
        Long memberId,
        String nickname,
        String profileImg,
        Gender gender,
        Integer age
) {

    @QueryProjection
    public AuthorDto {
    }
}
