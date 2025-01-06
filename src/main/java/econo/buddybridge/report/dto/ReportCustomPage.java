package econo.buddybridge.report.dto;

import java.util.List;

public record ReportCustomPage(
        List<ReportListItem> content,
        Long totalElements,
        Boolean last
) {

}
