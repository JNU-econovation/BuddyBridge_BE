package econo.buddybridge.report.dto;

import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;

public record MatchingReportDetailResponse(
        Long id,
        PostType postType,
        Long postId,
        Long matchingId,
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
