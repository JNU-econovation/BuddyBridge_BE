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
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic","/api/queue");
        registry.setApplicationDestinationPrefixes("/api/app");
        registry.setPreservePublishOrder(true); // 발행 순서 보장
    }


}
