package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ExpiredTokenException extends BusinessException {

    public static final BusinessException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(JwtErrorCode.EXPIRED_TOKEN);
    }
}
