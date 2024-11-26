package econo.buddybridge.config;


import econo.buddybridge.auth.jwt.TokenType;
import econo.buddybridge.auth.jwt.service.JwtTokenProvider;
import econo.buddybridge.websocket.WebSocketPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final static String PREFIX = "Bearer ";
    private final static String AUTHORIZATION = "Authorization";

    @Override // 커스텀 헤더의 JWT를 가져옴
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            processAuthentication(accessor);
        }

        return message;
    }

    private void processAuthentication(StompHeaderAccessor accessor) {

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("STOMP COMMAND : {}", accessor.getCommand());
            Long memberId = validateAndGetMemberId(accessor);
            setPrincipal(accessor, memberId);
        }

        if (StompCommand.SEND.equals(accessor.getCommand())) {
            validateAndGetMemberId(accessor);
        }
    }

    private Long validateAndGetMemberId(StompHeaderAccessor accessor) {
        String token = jwtTokenProvider.extractToken(accessor.getFirstNativeHeader(AUTHORIZATION));
        jwtTokenProvider.validateToken(token, TokenType.ACCESS);
        return jwtTokenProvider.getMemberIdFromAccessToken(token);
    }

    private void setPrincipal(StompHeaderAccessor accessor, Long memberId) {
        
        if (accessor.getUser() == null) {
            accessor.setUser(WebSocketPrincipal.of(memberId));
        }
    }
}
