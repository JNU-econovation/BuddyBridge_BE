package econo.buddybridge.auth.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoTokens {

    private String tokenType;

    private String accessToken;

    private int expiresIn;

    private String refreshToken;

    private int refreshTokenExpiresIn;

    private String scope;
}
