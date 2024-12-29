package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class OnlyDoneStateTransitionAllowedException extends BusinessException {

    public static final BusinessException EXCEPTION = new OnlyDoneStateTransitionAllowedException();

    private OnlyDoneStateTransitionAllowedException() {
        super(MatchingStateErrorCode.ONLY_DONE_STATE_TRANSITION_ALLOWED);
    }
}
