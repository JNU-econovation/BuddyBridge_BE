package econo.buddybridge.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.ScheduleType;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CompletedVolunteerScheduleDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType,
        LocalTime assistanceStartTime,
        LocalTime assistanceEndTime
) {

    @QueryProjection
    public CompletedVolunteerScheduleDto {
    }
}
