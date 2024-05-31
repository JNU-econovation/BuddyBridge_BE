package econo.buddybridge.auth;

import lombok.Getter;

@Getter
public enum OAuthProvider {
    KAKAO("kakao"), NAVER("naver"), GOOGLE("google");

    private final String provider;

    OAuthProvider(String provider) {
        this.provider = provider;
    }
}
