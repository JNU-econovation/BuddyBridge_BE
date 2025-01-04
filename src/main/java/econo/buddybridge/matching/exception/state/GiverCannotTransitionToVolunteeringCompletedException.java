package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class GiverCannotTransitionToVolunteeringCompletedException extends BusinessException {

    public static final BusinessException EXCEPTION = new GiverCannotTransitionToVolunteeringCompletedException();

    private GiverCannotTransitionToVolunteeringCompletedException() {
        super(MatchingStateErrorCode.GIVER_CANNOT_TRANSITION_TO_VOLUNTEERING_COMPLETED);
    }
}
