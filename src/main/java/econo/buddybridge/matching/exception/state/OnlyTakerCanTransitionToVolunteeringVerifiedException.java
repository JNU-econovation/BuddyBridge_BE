package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class OnlyTakerCanTransitionToVolunteeringVerifiedException extends BusinessException {

    public static final BusinessException EXCEPTION = new OnlyTakerCanTransitionToVolunteeringVerifiedException();

    private OnlyTakerCanTransitionToVolunteeringVerifiedException() {
        super(MatchingStateErrorCode.ONLY_TAKER_CAN_TRANSITION_TO_VOLUNTEERING_VERIFIED);
    }
}
