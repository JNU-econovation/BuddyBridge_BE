package econo.buddybridge.blacklist.exception;

import econo.buddybridge.common.exception.BusinessException;

public class BlackListRequestForbidden extends BusinessException {

    public static final BusinessException EXCEPTION = new BlackListRequestForbidden();

    private BlackListRequestForbidden() {
        super(BlackListErrorCode.BLACK_LIST_REQUEST_FORBIDDEN);
    }
}
