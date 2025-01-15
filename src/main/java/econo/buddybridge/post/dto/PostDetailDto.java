package econo.buddybridge.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record PostDetailDto(
        PostAuthorDto author,
        PostDetailInfoDto post
) {

    @QueryProjection
    public PostDetailDto {
    }
}
