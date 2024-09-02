package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class PostDeleteNotAllowedException extends BusinessException {

    public static BusinessException EXCEPTION = new PostDeleteNotAllowedException();

    private PostDeleteNotAllowedException() {
        super(PostErrorCode.POST_DELETE_NOT_ALLOWED);
    }
}
