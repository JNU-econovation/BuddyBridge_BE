package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.ScheduleType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostUpdateReqDto(
        String title,
        AssistanceType assistanceType,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType,
        String scheduleDetails,
        District district,
        String content,
        DisabilityType disabilityType,
        Gender gender,
        Integer age,
        LocalDateTime assistanceStartTime,
        LocalDateTime assistanceEndTime,
        Integer headcount
) {
}
