package econo.buddybridge.member.exception;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {

    public static BusinessException EXCEPTION = new InvalidPasswordException();

    private InvalidPasswordException() {
        super(MemberErrorCode.INVALID_PASSWORD);
    }
}
