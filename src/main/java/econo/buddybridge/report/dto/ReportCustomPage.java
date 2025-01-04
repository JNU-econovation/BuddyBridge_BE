package econo.buddybridge.report.dto;

import econo.buddybridge.report.entity.Report;
import econo.buddybridge.report.mapper.ReportMapper;
import java.util.List;

public record ReportCustomPage(
        List<ReportListItem> content,
        Long totalElements,
        Boolean last
) {

    public static <T extends Report> ReportCustomPage of(List<T> reports, Long totalElements, Boolean last) {
        List<ReportListItem> content = reports.stream()
                .map(ReportMapper::toReportListItem)
                .toList();
        return new ReportCustomPage(content, totalElements, last);
    }
}
