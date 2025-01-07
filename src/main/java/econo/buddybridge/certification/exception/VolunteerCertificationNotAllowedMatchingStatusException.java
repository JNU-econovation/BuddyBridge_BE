package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class VolunteerCertificationNotAllowedMatchingStatusException extends BusinessException {

    public static final BusinessException EXCEPTION = new VolunteerCertificationNotAllowedMatchingStatusException();

    private VolunteerCertificationNotAllowedMatchingStatusException() {
        super(VolunteerCertificationErrorCode.VOLUNTEER_CERTIFICATION_NOT_ALLOWED_MATCHING_STATUS);
    }
}
