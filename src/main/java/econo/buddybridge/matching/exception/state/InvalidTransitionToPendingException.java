package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransitionToPendingException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransitionToPendingException();

    private InvalidTransitionToPendingException() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION_TO_PENDING);
    }
}
