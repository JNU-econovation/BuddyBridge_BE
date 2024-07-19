package econo.buddybridge.auth.dto;

public interface OAuthInfoResponse {
    String getEmail();
    String getName();
    String getNickname();
    Integer getAge();
    String getGender();
    String getProfileImageUrl();
}
