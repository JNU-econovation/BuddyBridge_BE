package econo.buddybridge.blacklist.exception;

import econo.buddybridge.common.exception.BusinessException;

public class BlackListNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new BlackListNotFoundException();

    private BlackListNotFoundException() {
        super(BlackListErrorCode.BLACK_LIST_NOT_FOUND);
    }
}
