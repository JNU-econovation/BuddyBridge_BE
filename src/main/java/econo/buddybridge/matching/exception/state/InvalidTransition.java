package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidTransition extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTransition();

    private InvalidTransition() {
        super(MatchingStateErrorCode.INVALID_STATE_TRANSITION);
    }
}
