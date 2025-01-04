package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new MatchingAlreadyExistsException();

    private MatchingAlreadyExistsException() {
        super(MatchingErrorCode.MATCHING_ALREADY_EXISTS);
    }
}
