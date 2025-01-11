package econo.buddybridge.comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CommentDeleteRequest(
        @NotNull(message = "삭제할 댓글 ID는 필수입니다.")
        @Size(min = 1, message = "삭제할 댓글 ID는 최소 1개 이상이어야 합니다.")
        List<Long> commentIds
) {

}
