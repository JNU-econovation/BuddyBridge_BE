package econo.buddybridge.auth.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import econo.buddybridge.auth.dto.OAuthInfoResponse;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoInfoResponse implements OAuthInfoResponse {

    private KakaoAccount kakaoAccount;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoAccount {

        private KakaoProfile profile;

        private String name;

        private String email;

        private String birthyear;

        private String gender;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoProfile {

        private String nickname;

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
