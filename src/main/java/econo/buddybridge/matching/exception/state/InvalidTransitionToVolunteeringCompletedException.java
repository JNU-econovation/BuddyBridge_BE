package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransitionToVolunteeringCompletedException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransitionToVolunteeringCompletedException();

    private InvalidTransitionToVolunteeringCompletedException() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION_TO_VOLUNTEERING_COMPLETED);
    }
}
