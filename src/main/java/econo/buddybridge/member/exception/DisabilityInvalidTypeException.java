package econo.buddybridge.member.exception;

import econo.buddybridge.common.exception.BusinessException;

public class DisabilityInvalidTypeException extends BusinessException {

    public static final BusinessException EXCEPTION = new DisabilityInvalidTypeException();

    private DisabilityInvalidTypeException() {
        super(MemberErrorCode.DISABILITY_INVALID_TYPE);
    }
}
