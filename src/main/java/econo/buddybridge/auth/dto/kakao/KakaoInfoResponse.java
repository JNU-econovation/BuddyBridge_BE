package econo.buddybridge.auth.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import econo.buddybridge.auth.dto.OAuthInfoResponse;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {

        @JsonProperty("profile")
        private KakaoProfile profile;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("birthyear")
        private String birthyear;

        @JsonProperty("gender")
        private String gender;
    }

    @Data
    public static class KakaoProfile {

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("profile_image_url")
        private String profileImageUrl;
    }

    @Override
    public String getName() {
        return kakaoAccount.getName();
    }

    @Override
    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    @Override
    public String getNickname() {
        return kakaoAccount.getProfile().getNickname();
    }

    @Override
    public Integer getAge() {
        String birthyear = kakaoAccount.getBirthyear();
        if (birthyear == null) {
            return null;
        }
        return LocalDateTime.now().getYear() - Integer.parseInt(birthyear) + 1;
    }

    @Override
    public String getGender() {
        return kakaoAccount.getGender();
    }

    @Override
    public String getProfileImageUrl() {
        return kakaoAccount.getProfile().getProfileImageUrl();
    }
}
