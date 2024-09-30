package econo.buddybridge.comment.dto;

import java.util.List;

public record MyPageCommentCustomPage(
        List<MyPageCommentResDto> content,
        Long totalElements,
        Boolean last
) {

}
