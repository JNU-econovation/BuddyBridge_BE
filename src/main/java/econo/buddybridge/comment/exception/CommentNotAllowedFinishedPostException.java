package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentNotAllowedFinishedPostException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentNotAllowedFinishedPostException();

    private CommentNotAllowedFinishedPostException() {
        super(CommentErrorCode.COMMENT_NOT_ALLOWED_FINISHED_POST);
    }
}
