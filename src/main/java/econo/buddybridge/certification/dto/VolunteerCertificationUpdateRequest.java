package econo.buddybridge.certification.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public record VolunteerCertificationUpdateRequest(

        @NotNull(message = "봉사 일자는 필수 입니다.")
        LocalDate volunteerDate,

        @NotNull(message = "봉사 시작 시간은 필수 입니다.")
        LocalTime startTime,

        @NotNull(message = "봉사 종료 시간은 필수 입니다.")
        LocalTime endTime,

        @NotBlank(message = "봉사 내용은 필수 입니다.")
        @Size(min = 200, max = 1000, message = "봉사 활동 내용 및 소감은 200자 이상 1000자 이하로 작성해주세요.")
        String content
) {

    @AssertTrue(message = "봉사 시작 시간은 봉사 종료 시간보다 빨라야 합니다.")
    private boolean isStartTimeBeforeEndTime() {
        return !startTime.isAfter(endTime);
    }
}
