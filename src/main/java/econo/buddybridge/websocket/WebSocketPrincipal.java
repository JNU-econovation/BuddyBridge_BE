package econo.buddybridge.websocket;

import lombok.Getter;

import java.security.Principal;

@Getter
public class WebSocketPrincipal implements Principal {
    private final Long senderId;

    private WebSocketPrincipal(Long senderId) {
        this.senderId = senderId;
    }

    public static WebSocketPrincipal of(Long senderId) {
        return new WebSocketPrincipal(senderId);
    }

    @Override
    public String getName() {
        return senderId.toString();
    }
}
