package econo.buddybridge.report.dto;

import java.time.LocalDate;

public record MatchingReportDetailResponse(
        Long id,
        Long postId,
        Long matchingId,
        String reportedContent,
        String reporterName,
        String reportedName,
        String reportType,
        LocalDate reportDate,
        String reportReason
) implements ReportDetailResponse {

}
