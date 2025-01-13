package econo.buddybridge.report.dto;

import econo.buddybridge.post.entity.PostType;

public record ParsedReportInfo(
        Long postId,
        PostType postType,
        String reportedContent
) {

}
