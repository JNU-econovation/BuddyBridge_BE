package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidAccessTokenException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidAccessTokenException();

    private InvalidAccessTokenException() {
        super(JwtErrorCode.INVALID_ACCESS_TOKEN);
    }
}
