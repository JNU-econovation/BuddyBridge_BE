package econo.buddybridge.post.dto;

import econo.buddybridge.common.validation.EnumTypeValue;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.ScheduleType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record PostUpdateReqDto(
        String title,

        @EnumTypeValue(enumClass = AssistanceType.class, message = "도움 종류를 선택해주세요. 교육 or 생활")
        String assistanceType,

        LocalDateTime startDate,
        LocalDateTime endDate,

        ScheduleType scheduleType,
        String scheduleDetails,

        District district,

        String content,

        @EnumTypeValue(enumClass = DisabilityType.class)
        String disabilityType,

        Gender gender,
        Integer age,

        LocalTime assistanceStartTime,
        LocalTime assistanceEndTime
) {

}
