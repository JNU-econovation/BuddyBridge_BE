package econo.buddybridge.member.exception;

import econo.buddybridge.common.exception.BusinessException;

public class InvalidPasswordOrEmailException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidPasswordOrEmailException();

    private InvalidPasswordOrEmailException() {
        super(MemberErrorCode.INVALID_PASSWORD_OR_EMAIL);
    }
}
