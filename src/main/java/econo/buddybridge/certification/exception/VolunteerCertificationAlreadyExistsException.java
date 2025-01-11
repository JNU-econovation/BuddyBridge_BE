package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationAlreadyExistsException();

    private VolunteerCertificationAlreadyExistsException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_ALREADY_EXISTS);
    }
}
