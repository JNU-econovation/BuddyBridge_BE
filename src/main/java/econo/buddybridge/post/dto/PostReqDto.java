package econo.buddybridge.post.dto;

import econo.buddybridge.post.entity.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostReqDto(
        Long memberId,
        String title,
        AssistanceType assistanceType,
        LocalDateTime startTime,
        LocalDateTime endTime,
        ScheduleType scheduleType,
        String scheduleDetails,
        District district,
        String content,
        PostType postType,
        PostStatus postStatus
) {
}
