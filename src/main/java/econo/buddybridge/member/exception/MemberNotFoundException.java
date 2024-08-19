package econo.buddybridge.member.exception;

import econo.buddybridge.common.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new MemberNotFoundException();

    private MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }

}
