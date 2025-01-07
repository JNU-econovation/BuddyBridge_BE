package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationVolunteerMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationVolunteerMismatchException();

    private VolunteerCertificationVolunteerMismatchException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_VOLUNTEER_MISMATCH);
    }
}
