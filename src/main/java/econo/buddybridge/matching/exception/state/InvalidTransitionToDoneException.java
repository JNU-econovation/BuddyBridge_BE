package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransitionToDoneException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransitionToDoneException();

    private InvalidTransitionToDoneException() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION_TO_DONE);
    }
}
