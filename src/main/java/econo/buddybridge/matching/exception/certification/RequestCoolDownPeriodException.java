package econo.buddybridge.matching.exception.certification;

import econo.buddybridge.common.exception.BusinessException;

public class RequestCoolDownPeriodException extends BusinessException {

    public static final BusinessException EXCEPTION = new RequestCoolDownPeriodException();

    private RequestCoolDownPeriodException() {
        super(CertificationTrackingErrorCode.REQUEST_COOLDOWN_PERIOD);
    }
}
