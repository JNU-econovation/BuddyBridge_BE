package econo.buddybridge.config;


import econo.buddybridge.auth.jwt.TokenType;
import econo.buddybridge.auth.jwt.exception.InvalidAccessTokenException;
import econo.buddybridge.auth.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final static String PREFIX = "Bearer ";
    private final static String AUTHORIZATION = "Authorization";
    private final static String MEMBER_ID = "memberId";

    @Override // 커스텀 헤더의 JWT를 가져옴
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        processAuthentication(accessor);
        return message;
    }

    private void processAuthentication(StompHeaderAccessor accessor) {
        if (accessor != null && accessor.getCommand() == StompCommand.CONNECT) { // 연결 시 헤더 확인
            String token = Objects.requireNonNull(accessor.getFirstNativeHeader(AUTHORIZATION));

            if (!token.startsWith(PREFIX)) {
                throw InvalidAccessTokenException.EXCEPTION;
            }

            token = token.replace(PREFIX, "");
            validateAndSetHeader(token, accessor);
        }
    }

    private void validateAndSetHeader(String token, StompHeaderAccessor accessor) {
        jwtTokenProvider.validateToken(token, TokenType.ACCESS);
        Long memberId = jwtTokenProvider.getMemberIdFromAccessToken(token);
        accessor.addNativeHeader(MEMBER_ID, memberId.toString());
        accessor.setUser(new Principal() {
            @Override
            public String getName() {
                return memberId.toString();
            }
        });
    }
}
