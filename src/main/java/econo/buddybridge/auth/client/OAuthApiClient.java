package econo.buddybridge.auth.client;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.OAuthInfoResponse;
import econo.buddybridge.auth.dto.OAuthLoginParams;

public interface OAuthApiClient {
    OAuthProvider getOAuthProvider();
    String getAccessToken(OAuthLoginParams params);
    OAuthInfoResponse getUserInfo(String accessToken);
    void logout();
}
