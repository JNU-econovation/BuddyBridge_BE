package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentSelfNotAllowedException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentSelfNotAllowedException();

    private CommentSelfNotAllowedException() {
        super(CommentErrorCode.COMMENT_SELF_NOT_ALLOWED);
    }
}
