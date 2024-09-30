package econo.buddybridge.comment.dto;

import lombok.Builder;

@Builder
public record CommentReqDto(
        String content
) {

}
