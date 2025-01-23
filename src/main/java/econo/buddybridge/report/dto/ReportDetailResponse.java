package econo.buddybridge.report.dto;

import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;

public interface ReportDetailResponse {

    Long id();

    PostType postType();

    String reportedContent();

    Long reporterId();

    String reporterName();

    Long reportedId();

    String reportedName();

    String reportType();

    LocalDate reportDate();

    String reportReason();
}
