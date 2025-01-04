package econo.buddybridge.post.dto;

import lombok.Builder;

@Builder
public record PostDetailDto(
        PostAuthorDto author,
        PostDetailInfoDto post
) {

}
