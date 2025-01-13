package econo.buddybridge.report.dto;

import econo.buddybridge.common.validation.EnumTypeValue;
import econo.buddybridge.report.entity.ReportType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportRequest(
        @EnumTypeValue(enumClass = ReportType.class)
        String reportType,

        @NotNull(message = "신고 사유는 NULL일 수 없습니다.")
        @Size(max = 500, message = "신고 사유는 500자 이하로 입력해야 합니다.")
        String reportReason
) {

    @AssertTrue(message = "기타 신고 사유는 반드시 입력해야 합니다.")
    private boolean isEtcContentNotEmpty() {
        return !ReportType.기타.getValue().equals(reportType) ||
                (reportReason != null && !reportReason.isBlank());
    }
}
