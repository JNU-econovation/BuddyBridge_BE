package econo.buddybridge.config;

import econo.buddybridge.auth.resolver.MemberTokenResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final MemberTokenResolver memberTokenResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {    // CORS 설정
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://localhost:3000", "https://localhost:3000",
                        "http://localhost:8080", "https://localhost:8080",
                        "http://localhost:8081", "https://localhost:8081",
                        "https://buddy-bridge.vercel.app/",
                        "https://buddy-bridge-develop-server.vercel.app/"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/oauth/login",
                        "/api/oauth/logout",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberTokenResolver);
    }
}
