package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class PostTypeInvalidException extends BusinessException {

    public static final BusinessException EXCEPTION = new PostTypeInvalidException();

    private PostTypeInvalidException() {
        super(PostErrorCode.POST_TYPE_INVALID);
    }
}
