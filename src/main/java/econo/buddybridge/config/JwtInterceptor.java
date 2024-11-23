package econo.buddybridge.config;

import econo.buddybridge.auth.jwt.TokenType;
import econo.buddybridge.auth.jwt.service.JwtTokenProvider;
import econo.buddybridge.common.annotation.AllowAnonymous;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        // CORS preflight 요청은 토큰 검증을 하지 않음
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        // AllowAnonymous 어노테이션이 붙어있는 경우 토큰 검증을 하지 않음
        if (handler instanceof HandlerMethod handlerMethod && handlerMethod.getMethodAnnotation(AllowAnonymous.class) != null) {
            return true;
        }

        String token = jwtTokenProvider.extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));

        // reissue 엔드포인트로 요청이 들어오면 refresh token 검증
        if (request.getRequestURI().contains("/reissue")) {
            return jwtTokenProvider.validateToken(token, TokenType.REFRESH);
        }

        // Access Token에서 memberId 추출 후 Refresh Token이 tokenRepository에 존재하는지 확인
        Long memberId = jwtTokenProvider.getMemberIdFromAccessToken(token);
        jwtTokenProvider.existsByMemberId(memberId);

        return jwtTokenProvider.validateToken(token, TokenType.ACCESS);
    }
}
