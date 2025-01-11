package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationScheduleDateMismatch extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationScheduleDateMismatch();

    private VolunteerCertificationScheduleDateMismatch() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_SCHEDULE_DATE_MISMATCH);
    }
}
