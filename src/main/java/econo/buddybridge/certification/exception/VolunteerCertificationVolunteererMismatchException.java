package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationVolunteererMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationVolunteererMismatchException();
    
    private VolunteerCertificationVolunteererMismatchException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_VOLUNTEERER_MISMATCH);
    }
}
