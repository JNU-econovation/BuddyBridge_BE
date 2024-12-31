package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class DuplicateMatchingException extends BusinessException {

    public static final BusinessException EXCEPTION = new DuplicateMatchingException();

    private DuplicateMatchingException() {
        super(MatchingErrorCode.DUPLICATE_MATCHING);
    }
}
