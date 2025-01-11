package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationNotFoundException();

    private VolunteerCertificationNotFoundException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_NOT_FOUND);
    }
}
