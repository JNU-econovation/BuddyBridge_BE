package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentUpdateNotAllowedException extends BusinessException {

    public static BusinessException EXCEPTION = new CommentUpdateNotAllowedException();

    private CommentUpdateNotAllowedException() {
        super(CommentErrorCode.COMMENT_UPDATE_NOT_ALLOWED);
    }

}
