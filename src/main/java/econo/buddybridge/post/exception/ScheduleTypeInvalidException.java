package econo.buddybridge.post.exception;

import econo.buddybridge.common.exception.BusinessException;

public class ScheduleTypeInvalidException extends BusinessException {

    public static final BusinessException EXCEPTION = new ScheduleTypeInvalidException();

    private ScheduleTypeInvalidException() {
        super(PostErrorCode.SCHEDULE_TYPE_INVALID);
    }
}
