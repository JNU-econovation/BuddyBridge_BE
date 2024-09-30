package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentDeleteNotAllowedException extends BusinessException {

    public static BusinessException EXCEPTION = new CommentDeleteNotAllowedException();

    private CommentDeleteNotAllowedException() {
        super(CommentErrorCode.COMMENT_DELETE_NOT_ALLOWED);
    }
}
