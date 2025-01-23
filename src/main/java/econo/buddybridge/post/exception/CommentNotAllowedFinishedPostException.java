package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentNotAllowedFinishedPostException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentNotAllowedFinishedPostException();

    private CommentNotAllowedFinishedPostException() {
        super(PostErrorCode.COMMENT_NOT_ALLOWED_FINISHED_POST);
    }
}
