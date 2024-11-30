package econo.buddybridge.report.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ReportCommentAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new ReportCommentAlreadyExistsException();

    private ReportCommentAlreadyExistsException() {
        super(ReportErrorCode.REPORT_COMMENT_ALREADY_EXISTS);
    }
}
