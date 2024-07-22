package econo.buddybridge.auth.dto.kakao;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.OAuthLoginParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {

    private String authorizationCode;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getCode() {
        return authorizationCode;
    }
}
