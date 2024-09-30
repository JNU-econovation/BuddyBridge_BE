package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class PostUpdateNotAllowedException extends BusinessException {

    public static BusinessException EXCEPTION = new PostUpdateNotAllowedException();

    private PostUpdateNotAllowedException() {
        super(PostErrorCode.POST_UPDATE_NOT_ALLOWED);
    }
}
