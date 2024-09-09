package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.BusinessException;

public class SessionAlreadyExistsException extends BusinessException {

    public static BusinessException EXCEPTION = new SessionAlreadyExistsException();

    private SessionAlreadyExistsException() {
        super(AuthErrorCode.SESSION_ALREADY_EXISTS);
    }
}
