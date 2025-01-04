package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.BusinessException;

public class AccessDeniedException extends BusinessException {

    public static final BusinessException EXCEPTION = new AccessDeniedException();

    private AccessDeniedException() {
        super(AuthErrorCode.ACCESS_DENIED);
    }
}
