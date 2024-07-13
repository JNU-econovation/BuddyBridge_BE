package econo.buddybridge.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.MapSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<MapSession> {
    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket/connect") // ws://{BASE_URL}/socket/connect 로 연결 설정
                .setAllowedOriginPatterns("*") // CORS 허용
                .addInterceptors(new HttpSessionHandshakeInterceptor());
//                .withSockJS(); // WebSocket과 유사한 통신 가능하게 해주는 라이브러리
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){

        registry.enableSimpleBroker("/api/queue"); // /api/queue/chat/{room-id} // 메시지 받기
        // 구독 경로 설정에 사용(서버 -> 클라이언트)

        registry.setApplicationDestinationPrefixes("/api/app"); // /api/app/chat/{room-id} // 메시지 보내기
        // 발행 경로에 사용(클라이언트 -> 서버)

        registry.setPreservePublishOrder(true); // 발행 순서 보장
    }
}