package econo.buddybridge.report.entity;

import econo.buddybridge.common.validation.ValueEnum;
import econo.buddybridge.report.exception.ReportInvalidTypeException;
import java.util.Arrays;

public enum ReportType implements ValueEnum<String> {
    욕설_혐오_차별적_표현("욕설/혐오/차별적 표현"),
    불쾌한_표현("불쾌한 표현"),
    스팸_홍보_도배글("스팸/홍보/도배글"),
    불법정보_포함("불법정보 포함"),
    기타("기타")
    ;

    private final String value;

    ReportType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static ReportType fromValue(String value) {
        return Arrays.stream(ReportType.values())
                .filter(reportType -> reportType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> ReportInvalidTypeException.EXCEPTION);
    }
}
