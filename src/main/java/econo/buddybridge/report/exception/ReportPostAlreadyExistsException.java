package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportPostAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportPostAlreadyExistsException();

    private ReportPostAlreadyExistsException() {
        super(ReportErrorCode.REPORT_POST_ALREADY_EXISTS);
    }
}
