package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidRefreshTokenException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidRefreshTokenException();

    private InvalidRefreshTokenException() {
        super(JwtErrorCode.INVALID_REFRESH_TOKEN);
    }
}
