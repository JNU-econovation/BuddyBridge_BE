package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransitionToVolunteeringVerifiedException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransitionToVolunteeringVerifiedException();

    private InvalidTransitionToVolunteeringVerifiedException() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION_TO_VOLUNTEERING_VERIFIED);
    }
}
