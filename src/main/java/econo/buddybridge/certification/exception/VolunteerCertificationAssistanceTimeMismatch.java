package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationAssistanceTimeMismatch extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationAssistanceTimeMismatch();

    private VolunteerCertificationAssistanceTimeMismatch() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_ASSISTANCE_TIME_MISMATCH);
    }
}
