package econo.buddybridge.report.dto;

import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;

public record CommentReportDetailResponse(
        Long id,
        PostType postType,
        Long postId,
        Long commentId,
        String reportedContent,
        Long reporterId,
        String reporterName,
        Long reportedId,
        String reportedName,
        String reportType,
        LocalDate reportDate,
        String reportReason
) implements ReportDetailResponse {

}
