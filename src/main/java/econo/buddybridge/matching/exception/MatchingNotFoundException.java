package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new MatchingNotFoundException();

    private MatchingNotFoundException() {
        super(MatchingErrorCode.MATCHING_NOT_FOUND);
    }
}
