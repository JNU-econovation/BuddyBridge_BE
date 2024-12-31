package econo.buddybridge.post.dto;

import java.util.List;

public record CompletedVolunteerPostPage(
        List<CompletedVolunteerPostDto> content,
        Long totalElements,
        Boolean last
) {

}
