package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportSelfPostException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportSelfPostException();

    private ReportSelfPostException() {
        super(ReportErrorCode.REPORT_SELF_POST);
    }
}
