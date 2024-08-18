package econo.buddybridge.config;

import econo.buddybridge.common.annotation.AllowAnonymous;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private final SessionValidator sessionValidator;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {   // @AllowAnonymous 어노테이션을 붙이면 세션 검증을 하지 않음
            if (handlerMethod.getMethodAnnotation(AllowAnonymous.class) != null) {
                return true;
            }
        }

        if (CorsUtils.isPreFlightRequest(request)) {    // CORS preflight 요청은 세션 검증을 하지 않음
            return true;
        }

        return sessionValidator.validate(request, response);
    }
}
