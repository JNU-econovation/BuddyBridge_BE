package econo.buddybridge.auth.client.kakao.feign;

import econo.buddybridge.auth.dto.kakao.KakaoInfoResponse;
import econo.buddybridge.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "kakaoInfo", url = "${oauth.kakao.url.api-url}", configuration = OpenFeignConfig.class)
public interface KakaoInfoFeignClient {

    @PostMapping(value = "/v2/user/me")
    KakaoInfoResponse getUserInfo(
            @RequestHeader("Authorization") String accessToken
    );
}
