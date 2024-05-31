package econo.buddybridge.auth.client.kakao.feign;

import econo.buddybridge.auth.dto.kakao.KakaoTokens;
import econo.buddybridge.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "kakaoLogin", url = "${oauth.kakao.url.auth-url}", configuration = OpenFeignConfig.class)
public interface KakaoLoginFeignClient {

    @PostMapping(value = "/oauth/token")
    KakaoTokens getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code);

    @GetMapping(value = "/oauth/logout")
    void logout(
            @RequestParam("client_id") String clientId,
            @RequestParam("logout_redirect_uri") String logoutRedirectUri);
}
