package econo.buddybridge.post.dto;

import econo.buddybridge.common.validation.EnumTypeValue;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.ScheduleType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record PostUpdateReqDto(
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 30, message = "제목은 30자 이내로 작성해주세요.")
        String title,

        @NotNull(message = "도움 유형을 선택해주세요. 학습, 식사, 이동, 기타")
        @EnumTypeValue(enumClass = AssistanceType.class, message = "도움 종류를 선택해주세요. 교육 or 생활")
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

        @NotNull(message = "봉사 시작 시간을 입력해주세요.")
        LocalTime assistanceStartTime,

        @NotNull(message = "봉사 종료 시간을 입력해주세요.")
        LocalTime assistanceEndTime
) {

    @AssertTrue(message = "시작일은 현재 날짜 부터 가능합니다.")
    private boolean isStartDateValid() {
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate date = LocalDate.now();
        return !startLocalDate.isBefore(date);
    }

    @AssertTrue(message = "종료일은 시작일 이후여야 합니다.")
    private boolean isEndDateValid() {
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate endLocalDate = endDate.toLocalDate();
        return !endLocalDate.isBefore(startLocalDate);
    }

    @AssertTrue(message = "봉사 종료 시간은 시작 시간 보다 나중에 와야 합니다.")
    private boolean isAssistanceTimeValid() {
        return assistanceEndTime.isAfter(assistanceStartTime);
    }
}
