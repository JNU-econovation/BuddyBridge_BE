package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportNotFoundException();

    private ReportNotFoundException() {
        super(ReportErrorCode.REPORT_NOT_FOUND);
    }
}
