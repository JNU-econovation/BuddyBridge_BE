package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingUnauthorizedAccessException extends BusinessException {

    public static BusinessException EXCEPTION = new MatchingUnauthorizedAccessException();

    private MatchingUnauthorizedAccessException() { super(MatchingErrorCode.MATCHING_UNAUTHORIZED_ACCESS); }
}
