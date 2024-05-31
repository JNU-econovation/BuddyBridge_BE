package econo.buddybridge.auth.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record KakaoErrorResponse (
        String error,
        @JsonProperty("error_description")
        String errorDescription,
        @JsonProperty("error_code")
        String errorCode
) {
}