package econo.buddybridge.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import econo.buddybridge.auth.dto.kakao.KakaoErrorResponse;
import econo.buddybridge.auth.exception.FeignKakaoException;
import econo.buddybridge.auth.exception.FeignRedirectException;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableFeignClients(basePackages = "econo.buddybridge.auth")
public class OpenFeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("Content-Type", "application/x-www-form-urlencoded");
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    static class FeignErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, feign.Response response) {

            byte[] bodyData;
            try {
                bodyData = response.body().asInputStream().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return switch (response.status()) {
                case 302 -> {
                    response.headers().get("Location").stream()
                            .findFirst().orElseGet(() -> {
                                log.error("Location header not found");
                                return "";
                            });
                    yield FeignRedirectException.EXCEPTION;
                }
                case 400 -> {
                    // KakaoErrorResponse 로 변환할 코드
                    ObjectMapper objectMapper = new ObjectMapper();
                    KakaoErrorResponse kakaoErrorResponse;
                    try {
                        String body = new String(bodyData, StandardCharsets.UTF_8);
                        kakaoErrorResponse = objectMapper.readValue(body, KakaoErrorResponse.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    log.info("Kakao Api Exception occurred: {}", kakaoErrorResponse.errorDescription());
                    yield FeignKakaoException.EXCEPTION;
                }
                case 404 -> {
                    log.error("404 error occurred: {}", response);
                    yield new Exception("404");
                }
                default -> {
                    log.error("Unexpected error occurred: {}", response);
                    yield new Exception("500");
                }
            };
        }
    }
}
