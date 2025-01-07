package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationPostTypeMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationPostTypeMismatchException();

    private VolunteerCertificationPostTypeMismatchException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_POST_TYPE_MISMATCH);
    }
}
