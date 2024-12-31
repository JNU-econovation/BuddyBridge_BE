package econo.buddybridge.matching.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MatchingNotParticipantException extends BusinessException {

    public static final BusinessException EXCEPTION = new MatchingNotParticipantException();

    private MatchingNotParticipantException() {
        super(MatchingErrorCode.MATCHING_NOT_PARTICIPANT);
    }
}
