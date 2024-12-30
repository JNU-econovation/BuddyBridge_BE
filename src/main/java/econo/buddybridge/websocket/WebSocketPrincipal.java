package econo.buddybridge.websocket;

import java.security.Principal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketPrincipal implements Principal {

    private final Long senderId;
    private Long matchingId;

    public static WebSocketPrincipal of(Long senderId) {
        return new WebSocketPrincipal(senderId);
    }

    @Override
    public String getName() {
        return senderId.toString();
    }
}
