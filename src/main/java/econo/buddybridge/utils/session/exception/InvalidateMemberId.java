package econo.buddybridge.utils.session.exception;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidateMemberId extends BusinessException {

    public static BusinessException EXCEPTION = new InvalidateMemberId();

    private InvalidateMemberId() {
        super(SessionErrorCode.INVALIDATE_MEMBER_ID);
    }
}
