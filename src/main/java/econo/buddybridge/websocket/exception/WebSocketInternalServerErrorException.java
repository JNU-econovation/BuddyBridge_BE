package econo.buddybridge.websocket.exception;

import econo.buddybridge.common.exception.BusinessException;

public class WebSocketInternalServerErrorException extends BusinessException {

    public static final BusinessException EXCEPTION = new WebSocketInternalServerErrorException();

    private WebSocketInternalServerErrorException() {
        super(WebSocketErrorCode.WS_INTERNAL_SERVER_ERROR);
    }
}
