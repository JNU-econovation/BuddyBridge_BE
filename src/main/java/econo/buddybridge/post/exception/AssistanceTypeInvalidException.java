package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class AssistanceTypeInvalidException extends BusinessException {

    public static final BusinessException EXCEPTION = new AssistanceTypeInvalidException();

    private AssistanceTypeInvalidException() {
        super(PostErrorCode.ASSISTANCE_TYPE_INVALID);
    }
}
