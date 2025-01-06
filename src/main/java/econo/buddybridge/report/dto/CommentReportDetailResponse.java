package econo.buddybridge.report.dto;

import java.time.LocalDate;

public record CommentReportDetailResponse(
        Long id,
        Long postId,
        Long commentId,
        String reportedContent,
        String reporterName,
        String reportedName,
        String reportType,
        LocalDate reportDate,
        String reportReason
) implements ReportDetailResponse {

}
