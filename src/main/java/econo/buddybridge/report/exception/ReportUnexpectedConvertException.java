package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportUnexpectedConvertException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportUnexpectedConvertException();

    private ReportUnexpectedConvertException() {
        super(ReportErrorCode.REPORT_UNEXPECTED_CONVERT);
    }
}
