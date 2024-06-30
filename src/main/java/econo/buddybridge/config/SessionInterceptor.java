package econo.buddybridge.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

        String requestURI = request.getRequestURI();
        if (requestURI.equals("/error")) {        // 오류로 인해 요청이 /error로 들어오는 경우
            return false;
        }

        String requestMethod = request.getMethod();
        if (requestMethod.equals("OPTIONS")) {    // CORS preflight 요청 처리
            return true;
        }

        if (requestMethod.equals("GET") && !requestURI.startsWith("/api/users")) {
            return true;
        }

        return sessionValidator.validate(request, response);
    }
}