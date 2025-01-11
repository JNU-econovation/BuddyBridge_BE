package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingStatusNotDoneException extends BusinessException {

    public static final BusinessException EXCEPTION = new MatchingStatusNotDoneException();

    private MatchingStatusNotDoneException() {
        super(MatchingErrorCode.MATCHING_STATUS_NOT_DONE);
    }
}
