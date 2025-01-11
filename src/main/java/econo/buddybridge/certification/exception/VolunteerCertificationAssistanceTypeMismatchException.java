package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationAssistanceTypeMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationAssistanceTypeMismatchException();

    private VolunteerCertificationAssistanceTypeMismatchException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_ASSISTANCE_TYPE_MISMATCH);
    }
}
