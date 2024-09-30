package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentInvalidDirectionException extends BusinessException {

    public static BusinessException EXCEPTION = new CommentInvalidDirectionException();

    private CommentInvalidDirectionException() {
        super(CommentErrorCode.COMMENT_INVALID_DIRECTION);
    }
}
