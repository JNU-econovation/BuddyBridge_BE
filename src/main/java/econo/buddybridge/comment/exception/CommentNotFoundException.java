package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new CommentNotFoundException();

    private CommentNotFoundException() {
        super(CommentErrorCode.COMMENT_NOT_FOUND);
    }
}
