package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class PostNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new PostNotFoundException();

    private PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
