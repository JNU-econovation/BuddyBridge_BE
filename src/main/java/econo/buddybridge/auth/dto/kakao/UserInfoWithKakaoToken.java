package econo.buddybridge.auth.dto.kakao;

import econo.buddybridge.auth.dto.OAuthInfoResponse;

public record UserInfoWithKakaoToken(
        OAuthInfoResponse info,
        String accessToken
) {

}
