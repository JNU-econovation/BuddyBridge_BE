package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.BusinessException;

public class EncryptFailedException extends BusinessException {

    public static BusinessException EXCEPTION = new EncryptFailedException();

    private EncryptFailedException() {
        super(EncoderErrorCode.ENCRYPT_FAILED);
    }
}
