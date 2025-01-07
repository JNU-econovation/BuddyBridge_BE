package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationAssistanceTimeMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationAssistanceTimeMismatchException();

    private VolunteerCertificationAssistanceTimeMismatchException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_ASSISTANCE_TIME_MISMATCH);
    }
}
