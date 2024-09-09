package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.BusinessException;

public class FeignKakaoException extends BusinessException {

    public static BusinessException EXCEPTION = new FeignKakaoException();

    private FeignKakaoException() {
        super(AuthErrorCode.FEIGN_KAKAO_EXCEPTION);
    }
}
