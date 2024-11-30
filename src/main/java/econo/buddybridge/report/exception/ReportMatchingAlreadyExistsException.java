package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportMatchingAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportMatchingAlreadyExistsException();

    private ReportMatchingAlreadyExistsException() {
        super(ReportErrorCode.REPORT_MATCHING_ALREADY_EXISTS);
    }
}
