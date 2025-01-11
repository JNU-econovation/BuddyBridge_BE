package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationAllowedOnlyVolunteeringCompletedException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationAllowedOnlyVolunteeringCompletedException();

    private VolunteerCertificationAllowedOnlyVolunteeringCompletedException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_ALLOWED_ONLY_VOLUNTEERING_COMPLETED);
    }
}
