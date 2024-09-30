package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class PostUnauthorizedAccessException extends BusinessException {

    public static BusinessException EXCEPTION = new PostUnauthorizedAccessException();

    private PostUnauthorizedAccessException() {
        super(PostErrorCode.POST_UNAUTHORIZED_ACCESS);
    }
}
