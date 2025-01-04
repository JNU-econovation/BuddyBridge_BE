package econo.buddybridge.report.dto;

import java.time.LocalDate;

public interface ReportDetailResponse {
    Long id();
    String reportedContent();
    String reporterName();
    String reportedName();
    String reportType();
    LocalDate reportDate();
    String reportReason();
}
