package econo.buddybridge.blacklist.exception;

import econo.buddybridge.common.exception.BusinessException;

public class BlackListAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new BlackListAlreadyExistsException();

    private BlackListAlreadyExistsException() {
        super(BlackListErrorCode.BLACK_LIST_ALREADY_EXISTS);
    }
}
