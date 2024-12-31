package econo.buddybridge.post.dto;

import econo.buddybridge.post.entity.ScheduleType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleListResDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType
) {
    
}
