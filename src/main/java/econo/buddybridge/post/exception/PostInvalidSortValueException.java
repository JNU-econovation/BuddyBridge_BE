package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class PostInvalidSortValueException extends BusinessException {

    public static BusinessException EXCEPTION = new PostInvalidSortValueException();

    private PostInvalidSortValueException() {
        super(PostErrorCode.POST_INVALID_SORT_VALUE);
    }
}
