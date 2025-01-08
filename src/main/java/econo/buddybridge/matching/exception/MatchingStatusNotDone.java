package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingStatusNotDone extends BusinessException {

    public static final BusinessException EXCEPTION = new MatchingStatusNotDone();

    private MatchingStatusNotDone() {
        super(MatchingErrorCode.MATCHING_STATUS_NOT_DONE);
    }
}
