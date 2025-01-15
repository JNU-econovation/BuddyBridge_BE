package econo.buddybridge.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.AssistanceType;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record AssistanceResDto(
        AssistanceType assistanceType,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceStartTime,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceEndTime
) {

    @QueryProjection
    public AssistanceResDto {
    }
}
