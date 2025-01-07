package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class UnexpectedVolunteeringVerifiedEventException extends BusinessException {

    public static final BusinessException EXCEPTION = new UnexpectedVolunteeringVerifiedEventException();

    private UnexpectedVolunteeringVerifiedEventException() {
        super(MatchingStateErrorCode.UNEXPECTED_VOLUNTEERING_VERIFIED_EVENT);
    }
}