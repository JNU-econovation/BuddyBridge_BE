package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransitionToFailedException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransitionToFailedException();

    private InvalidTransitionToFailedException() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION_TO_FAILED);
    }
}
