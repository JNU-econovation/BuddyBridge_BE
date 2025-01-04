package econo.buddybridge.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.ScheduleType;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ScheduleDetailResDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType,
        String scheduleDetails
) {

    @QueryProjection
    public ScheduleDetailResDto {
    }
}
