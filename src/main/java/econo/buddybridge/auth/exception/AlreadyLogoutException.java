package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.BusinessException;

public class AlreadyLogoutException extends BusinessException {

    public static BusinessException EXCEPTION = new AlreadyLogoutException();

    public AlreadyLogoutException() {
        super(AuthErrorCode.ALREADY_LOGOUT);
    }

}
