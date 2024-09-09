package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.BusinessException;

public class FeignRedirectException extends BusinessException {

    public static BusinessException EXCEPTION = new FeignRedirectException();

    private FeignRedirectException() {
        super(AuthErrorCode.FEIGN_REDIRECT);
    }
}
