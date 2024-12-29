package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransitionForVolunteeringVerifiedException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransitionForVolunteeringVerifiedException();

    private InvalidTransitionForVolunteeringVerifiedException() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION_FOR_VOLUNTEERING_VERIFIED);
    }
}