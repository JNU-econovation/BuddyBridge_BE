package econo.buddybridge.post.dto;

import econo.buddybridge.common.validation.EnumTypeValue;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.ScheduleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record PostReqDto(
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 30, message = "제목은 30자 이내로 작성해주세요.")
        String title,

        @NotNull(message = "도움 유형을 선택해주세요. 학습, 식사, 이동, 기타")
        @EnumTypeValue(enumClass = AssistanceType.class, message = "올바른 도움 유형을 선택해주세요. 학습, 식사, 이동, 기타")
        String assistanceType,

        @NotNull(message = "봉사 시작 날짜를 입력해주세요.")
        LocalDateTime startDate,

        @NotNull(message = "봉사 종료 날짜를 입력해주세요.")
        LocalDateTime endDate,

        @NotNull(message = "일정 유형을 선택해주세요. 정기 or 비정기")
        @EnumTypeValue(enumClass = ScheduleType.class, message = "올바른 일정 유형을 선택해주세요. 정기 or 비정기")
        String scheduleType,

        @NotBlank(message = "일정 상세를 입력해주세요. 예) 매주 월요일, 화목")
        String scheduleDetails,

        @NotNull(message = "지역을 선택해주세요.")
        District district,

        @NotBlank(message = "상세 내용을 입력해주세요.")
        @Size(max = 500, message = "상세 내용은 500자 이내로 작성해주세요.")
        String content,

        @NotNull(message = "게시글 종류를 선택해주세요. TAKER or GIVER")
        @EnumTypeValue(enumClass = PostType.class, message = "올바른 게시글 종류를 선택해주세요. TAKER or GIVER")
        String postType,

        @NotNull(message = "봉사 시작 시간을 입력해주세요.")
        LocalTime assistanceStartTime,

        @NotNull(message = "봉사 종료 시간을 입력해주세요.")
        LocalTime assistanceEndTime
) {

}
