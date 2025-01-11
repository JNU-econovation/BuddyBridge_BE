package econo.buddybridge.matching.exception.certification;

import econo.buddybridge.common.exception.BusinessException;

public class CertificationAlreadyCompletedException extends BusinessException {

    public static final BusinessException EXCEPTION = new CertificationAlreadyCompletedException();

    private CertificationAlreadyCompletedException() {
        super(CertificationTrackingErrorCode.CERTIFICATION_ALREADY_COMPLETED);
    }
}
