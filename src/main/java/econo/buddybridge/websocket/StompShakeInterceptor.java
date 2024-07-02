package econo.buddybridge.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class StompShakeInterceptor implements HandshakeInterceptor {
    // websocket 연결이 설정되기 전 호출
    // 특정 요청 검사 및 인증 토큰 검사
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("stomp handshake start");
        return true; // true : handshake 진행, false : 중단
    }

    // handshake 완료 후 호출
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("stomp handshake success");
    }

}
