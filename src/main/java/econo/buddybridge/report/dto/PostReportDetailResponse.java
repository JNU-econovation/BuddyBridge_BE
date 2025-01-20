package econo.buddybridge.report.dto;

import java.time.LocalDate;

public record PostReportDetailResponse(
        Long id,
        Long postId,
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
