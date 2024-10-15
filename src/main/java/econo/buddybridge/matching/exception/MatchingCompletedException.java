package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingCompletedException extends BusinessException {

    public static BusinessException EXCEPTION = new MatchingCompletedException();

    private MatchingCompletedException() {
        super(MatchingErrorCode.MATCHING_COMPLETED);
    }
}
