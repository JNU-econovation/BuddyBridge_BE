package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ExceededMatchingLimitException extends BusinessException {

    public static BusinessException EXCEPTION = new ExceededMatchingLimitException();

    private ExceededMatchingLimitException() {
        super(MatchingErrorCode.EXCEEDED_MATCHING_LIMIT);
    }
}
