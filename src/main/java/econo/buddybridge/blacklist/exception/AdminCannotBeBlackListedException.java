package econo.buddybridge.blacklist.exception;

import econo.buddybridge.common.exception.BusinessException;

public class AdminCannotBeBlackListedException extends BusinessException {

    public static final BusinessException EXCEPTION = new AdminCannotBeBlackListedException();

    private AdminCannotBeBlackListedException() {
        super(BlackListErrorCode.ADMIN_CANNOT_BE_BLACK_LISTED);
    }
}
