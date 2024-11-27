package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.BusinessException;

public class LoggedOutTokenException extends BusinessException {

    public static final BusinessException EXCEPTION = new LoggedOutTokenException();

    private LoggedOutTokenException() {
        super(JwtErrorCode.LOGGED_OUT_TOKEN);
    }
}
