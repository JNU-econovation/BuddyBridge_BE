package econo.buddybridge.member.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MemberEmailAlreadyExistsException extends BusinessException {

    public static BusinessException EXCEPTION = new MemberEmailAlreadyExistsException();

    private MemberEmailAlreadyExistsException() {
        super(MemberErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
    }
}
