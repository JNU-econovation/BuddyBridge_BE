package econo.buddybridge.member.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MemberNicknameAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new MemberNicknameAlreadyExistsException();

    private MemberNicknameAlreadyExistsException() {
        super(MemberErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }
}
