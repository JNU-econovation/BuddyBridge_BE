package econo.buddybridge.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import econo.buddybridge.post.entity.AssistanceType;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record AssistanceResDto(
        AssistanceType assistanceType,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceStartTime,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceEndTime
) {
}
