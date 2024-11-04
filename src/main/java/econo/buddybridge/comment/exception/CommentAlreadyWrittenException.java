package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentAlreadyWrittenException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentAlreadyWrittenException();

    private CommentAlreadyWrittenException() {
        super(CommentErrorCode.COMMENT_ALREADY_WRITTEN);
    }
}
