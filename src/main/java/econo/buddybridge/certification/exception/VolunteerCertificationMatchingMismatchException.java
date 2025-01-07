package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationMatchingMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationMatchingMismatchException();

    private VolunteerCertificationMatchingMismatchException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_MATCHING_MISMATCH);
    }
}
