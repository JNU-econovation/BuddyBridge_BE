package econo.buddybridge.report.dto;

import econo.buddybridge.report.entity.Report;

public record ReportWithBlackListInfo(
        Report report,
        Boolean isBlackListed
) {

}
