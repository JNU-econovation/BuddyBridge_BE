package econo.buddybridge.certification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.ScheduleType;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AdminCertificationPostDetailResponse(
        String title,

        District district,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime startDate,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime endDate,

        ScheduleType scheduleType,

        String scheduleDetails,

        LocalTime assistanceStartTime,

        LocalTime assistanceEndTime,

        String postContent
) {

}
