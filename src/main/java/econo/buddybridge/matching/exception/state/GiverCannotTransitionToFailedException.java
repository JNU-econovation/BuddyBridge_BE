package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class GiverCannotTransitionToFailedException extends BusinessException {

    public static final BusinessException EXCEPTION = new GiverCannotTransitionToFailedException();

    private GiverCannotTransitionToFailedException() {
        super(MatchingStateErrorCode.GIVER_CANNOT_TRANSITION_TO_FAILED);
    }
}
