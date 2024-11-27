package econo.buddybridge.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CommentReqDto(
        @NotBlank(message = "댓글을 입력해주세요.")
        @Size(max = 100, message = "댓글은 100자 이내로 작성해주세요.")
        String content
) {

}
