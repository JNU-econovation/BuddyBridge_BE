package econo.buddybridge.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    private final StompChannelInterceptor stompChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket/connect") // ws://{BASE_URL}/socket/connect 로 연결 설정
                .setAllowedOriginPatterns("*"); // CORS 허용
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/api/queue"); // /api/queue/chat/{room-id} // 메시지 받기
        // 구독 경로 설정에 사용(서버 -> 클라이언트)

        registry.setApplicationDestinationPrefixes("/api/app"); // /api/app/chat/{room-id} // 메시지 보내기
        // 발행 경로에 사용(클라이언트 -> 서버)

        registry.setPreservePublishOrder(true); // 발행 순서 보장
    }

    // Stomp 연결 시도 시 호출
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompChannelInterceptor);
    }
}
