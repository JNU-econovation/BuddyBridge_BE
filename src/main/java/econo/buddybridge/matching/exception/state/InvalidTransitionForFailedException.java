package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransitionForFailedException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransitionForFailedException();

    private InvalidTransitionForFailedException() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION_FOR_FAILED);
    }
}
