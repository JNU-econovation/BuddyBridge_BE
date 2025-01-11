package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationAssistanceTypeMismatch extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationAssistanceTypeMismatch();

    private VolunteerCertificationAssistanceTypeMismatch() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_ASSISTANCE_TYPE_MISMATCH);
    }
}
