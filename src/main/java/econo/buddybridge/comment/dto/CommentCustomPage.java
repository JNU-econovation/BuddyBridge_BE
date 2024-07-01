package econo.buddybridge.comment.dto;

import java.util.List;

public record CommentCustomPage(
    List<CommentResDto> content,
    Long cursor,
    Boolean nextPage
) {
}
