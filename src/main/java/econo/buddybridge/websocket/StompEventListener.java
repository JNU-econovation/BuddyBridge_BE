//package econo.buddybridge.websocket;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@Component
//@Slf4j
//public class StompEventListener {
//    // websocket 연결 성공시 호출
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectedEvent event){
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccessor.getSessionId();
//
//        // session id를 로그에 기록
//        log.info("[Connected] websocket session id : {}", sessionId);
//    }
//
//    // websocket 연결 종료시 호출
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccessor.getSessionId();
//
//        log.info("[Disconnected] websocket session id : {}", sessionId);
//    }
//
//}
