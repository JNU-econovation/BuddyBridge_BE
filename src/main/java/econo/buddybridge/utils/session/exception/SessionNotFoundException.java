package econo.buddybridge.utils.session.exception;

import econo.buddybridge.common.exception.BusinessException;

public class SessionNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new SessionNotFoundException();

    private SessionNotFoundException() {
        super(SessionErrorCode.SESSION_NOT_FOUND);
    }
}
