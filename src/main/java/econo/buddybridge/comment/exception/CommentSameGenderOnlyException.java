package econo.buddybridge.comment.exception;

import econo.buddybridge.common.exception.BusinessException;

public class CommentSameGenderOnlyException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentSameGenderOnlyException();
    
    private CommentSameGenderOnlyException() {
        super(CommentErrorCode.COMMENT_SAME_GENDER_ONLY);
    }
}
