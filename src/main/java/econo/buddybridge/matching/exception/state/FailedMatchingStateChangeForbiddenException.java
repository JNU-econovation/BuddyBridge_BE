package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class FailedMatchingStateChangeForbiddenException extends BusinessException {

    public static final BusinessException EXCEPTION = new FailedMatchingStateChangeForbiddenException();

    private FailedMatchingStateChangeForbiddenException() {
        super(MatchingStateErrorCode.FAILED_MATCHING_STATE_CHANGE_FORBIDDEN);
    }
}
