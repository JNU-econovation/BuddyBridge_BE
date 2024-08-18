package econo.buddybridge.notification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class NotificationNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new NotificationNotFoundException();

    public NotificationNotFoundException() {
        super(NotificationErrorCode.NOTIFICATION_NOT_FOUND);
    }

}
