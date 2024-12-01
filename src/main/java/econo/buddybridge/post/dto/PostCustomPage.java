package econo.buddybridge.post.dto;

import java.util.List;

public record PostCustomPage(
        List<PostListItemDto> content,
        Long totalElements,
        Boolean last
) {

}
