package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationAlreadyCertifiedException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationAlreadyCertifiedException();

    private VolunteerCertificationAlreadyCertifiedException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_ALREADY_CERTIFIED);
    }
}
