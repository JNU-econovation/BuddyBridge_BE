package econo.buddybridge.auth.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoErrorResponse(
        String error,
        String errorDescription,
        String errorCode
) {

}
