package econo.buddybridge.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // ws로 연결설정
                .setAllowedOriginPatterns("*") // CORS 허용
                .addInterceptors(new StompShakeInterceptor())
                .withSockJS(); // WebSocket과 유사한 통신 가능하게 해주는 라이브러리
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic","/api/queue");
        registry.setApplicationDestinationPrefixes("/api/app");
        registry.setPreservePublishOrder(true);
    }


}
