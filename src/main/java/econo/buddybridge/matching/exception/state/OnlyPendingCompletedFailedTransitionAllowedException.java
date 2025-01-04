package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class OnlyPendingCompletedFailedTransitionAllowedException extends BusinessException {

    public static final BusinessException EXCEPTION = new OnlyPendingCompletedFailedTransitionAllowedException();

    private OnlyPendingCompletedFailedTransitionAllowedException() {
        super(MatchingStateErrorCode.ONLY_PENDING_COMPLETED_FAILED_TRANSITIONS_ALLOWED);
    }
}
