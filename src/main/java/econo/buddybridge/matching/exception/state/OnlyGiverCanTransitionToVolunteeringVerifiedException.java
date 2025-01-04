package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class OnlyGiverCanTransitionToVolunteeringVerifiedException extends BusinessException {

    public static final BusinessException EXCEPTION = new OnlyGiverCanTransitionToVolunteeringVerifiedException();

    private OnlyGiverCanTransitionToVolunteeringVerifiedException() {
        super(MatchingStateErrorCode.ONLY_GIVER_CAN_TRANSITION_TO_VOLUNTEERING_VERIFIED);
    }
}
