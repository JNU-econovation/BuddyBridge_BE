package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentNotBelongToMatchingException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentNotBelongToMatchingException();

    private CommentNotBelongToMatchingException() {
        super(MatchingErrorCode.COMMENT_NOT_BELONG_TO_MATCHING);
    }
}
