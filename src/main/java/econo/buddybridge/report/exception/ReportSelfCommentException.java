package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportSelfCommentException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportSelfCommentException();

    private ReportSelfCommentException() {
        super(ReportErrorCode.REPORT_SELF_COMMENT);
    }
}
