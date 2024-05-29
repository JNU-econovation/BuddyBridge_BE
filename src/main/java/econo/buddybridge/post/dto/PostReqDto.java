package econo.buddybridge.post.dto;

import econo.buddybridge.post.entity.*;
import lombok.Builder;

@Builder
public record PostReqDto(
        Long memberId,
        String title,
        AssistanceType assistanceType,
        DurationPeriod durationPeriod,
        ScheduleType scheduleType,
        String scheduleDetail,
        District district,
        String content,
        PostType postType,
        PostStatus postStatus
) {
}
