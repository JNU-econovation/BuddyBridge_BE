package econo.buddybridge.auth.exception;

import econo.buddybridge.common.exception.BusinessException;

public class GenerateSaltFailedException extends BusinessException {

    public static BusinessException EXCEPTION = new GenerateSaltFailedException();

    private GenerateSaltFailedException() {
        super(EncoderErrorCode.GENERATE_SALT_FAILED);
    }
}
