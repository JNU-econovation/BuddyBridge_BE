package econo.buddybridge.config;

import econo.buddybridge.chat.chatmessage.service.MessageReadStatusService;
import econo.buddybridge.websocket.WebSocketPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class StompEventListener {

    private final MessageReadStatusService messageReadStatusService;

    // websocket 구독시 호출
    @EventListener
    public void handleWebSocketConnectListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        WebSocketPrincipal user = (WebSocketPrincipal) headerAccessor.getUser();

        Long matchingId = getMatchingIdFromDestination(headerAccessor);
        user.setMatchingId(matchingId);

        messageReadStatusService.updateLastReadTime(matchingId, user.getSenderId());
    }

    private Long getMatchingIdFromDestination(StompHeaderAccessor headerAccessor) {
        String destination = headerAccessor.getDestination();
        String matchingIdStr = destination.substring(destination.lastIndexOf("/") + 1);
        return Long.parseLong(matchingIdStr);
    }

    // websocket 연결 종료시 호출
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        WebSocketPrincipal user = (WebSocketPrincipal) headerAccessor.getUser();

        Long memberId = user.getSenderId();
        Long matchingId = user.getMatchingId();

        messageReadStatusService.updateLastReadTime(matchingId, memberId);
    }
}
