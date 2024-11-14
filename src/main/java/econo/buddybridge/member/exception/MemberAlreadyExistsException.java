package econo.buddybridge.member.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MemberAlreadyExistsException extends BusinessException {

    public static BusinessException EXCEPTION = new MemberAlreadyExistsException();

    private MemberAlreadyExistsException() {
        super(MemberErrorCode.MEMBER_ALREADY_EXISTS);
    }
}
