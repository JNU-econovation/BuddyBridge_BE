package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class AssistanceTypeInvaldException extends BusinessException {

    public static final BusinessException EXCEPTION = new AssistanceTypeInvaldException();

    private AssistanceTypeInvaldException() {
        super(PostErrorCode.ASSISTANCE_TYPE_INVALID);
    }
}
