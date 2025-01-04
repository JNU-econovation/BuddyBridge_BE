package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class VerifiedMatchingStateChangeForbiddenException extends BusinessException {

    public static final BusinessException EXCEPTION = new VerifiedMatchingStateChangeForbiddenException();

    private VerifiedMatchingStateChangeForbiddenException() {
        super(MatchingStateErrorCode.VERIFIED_MATCHING_STATE_CHANGE_FORBIDDEN);
    }
}