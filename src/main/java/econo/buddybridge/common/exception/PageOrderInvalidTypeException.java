package econo.buddybridge.common.exception;

public class PageOrderInvalidTypeException extends BusinessException {

    public static final BusinessException EXCEPTION = new PageOrderInvalidTypeException();

    private PageOrderInvalidTypeException() {
        super(CommonErrorCode.PAGE_ORDER_INVALID_TYPE);
    }
}
