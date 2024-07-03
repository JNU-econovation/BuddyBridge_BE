package econo.buddybridge.post.dto;

import java.util.List;

public record PostCustomPage(
        List<PostResDto> content,
        Long totalElements,
        Boolean last
) {

}
