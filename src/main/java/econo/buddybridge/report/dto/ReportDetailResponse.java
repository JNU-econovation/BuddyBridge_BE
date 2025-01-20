package econo.buddybridge.report.dto;

import java.time.LocalDate;

public interface ReportDetailResponse {
    Long id();
    String reportedContent();
    Long reporterId();
    String reporterName();
    Long reportedId();
    String reportedName();
    String reportType();
    LocalDate reportDate();
    String reportReason();
}
