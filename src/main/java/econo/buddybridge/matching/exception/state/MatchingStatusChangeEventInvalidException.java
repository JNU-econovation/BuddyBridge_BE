package econo.buddybridge.matching.exception.state;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingStatusChangeEventInvalidException extends BusinessException {

    public static final BusinessException EXCEPTION = new MatchingStatusChangeEventInvalidException();

    private MatchingStatusChangeEventInvalidException() {
        super(MatchingStateErrorCode.MATCHING_STATUS_CHANGE_EVENT_INVALID_TYPE);
    }
}
