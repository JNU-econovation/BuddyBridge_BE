package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportInvalidTypeException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportInvalidTypeException();

    private ReportInvalidTypeException() {
        super(ReportErrorCode.REPORT_INVALID_TYPE);
    }
}
