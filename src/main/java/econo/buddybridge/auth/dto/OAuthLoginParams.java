package econo.buddybridge.auth.dto;

import econo.buddybridge.auth.OAuthProvider;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    String getCode();
}
