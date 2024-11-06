package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTokenException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(JwtErrorCode.INVALID_TOKEN);
    }
}
