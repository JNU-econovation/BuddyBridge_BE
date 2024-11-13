package econo.buddybridge.auth.jwt.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MissingTokenException extends BusinessException {

    public static final BusinessException EXCEPTION = new MissingTokenException();

    private MissingTokenException() {
        super(JwtErrorCode.MISSING_TOKEN);
    }
}
