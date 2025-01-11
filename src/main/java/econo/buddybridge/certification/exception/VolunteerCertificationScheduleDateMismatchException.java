package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationScheduleDateMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationScheduleDateMismatchException();

    private VolunteerCertificationScheduleDateMismatchException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_SCHEDULE_DATE_MISMATCH);
    }
}
