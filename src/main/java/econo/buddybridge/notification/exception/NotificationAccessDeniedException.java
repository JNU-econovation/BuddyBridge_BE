package econo.buddybridge.notification.exception;

import econo.buddybridge.common.exception.BusinessException;

public class NotificationAccessDeniedException extends BusinessException {

    public static final BusinessException EXCEPTION = new NotificationAccessDeniedException();

    private NotificationAccessDeniedException() {
        super(NotificationErrorCode.NOTIFICATION_ACCESS_DENIED);
    }
}
