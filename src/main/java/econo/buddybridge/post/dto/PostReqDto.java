package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.AssistanceTime;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.Schedule;
import econo.buddybridge.post.entity.ScheduleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostReqDto(
        @NotBlank(message = "제목을 입력해주세요.")
        String title,

        @NotNull(message = "도움 종류를 선택해주세요. 교육 or 생활")
        AssistanceType assistanceType,

        @NotNull(message = "봉사 시작 날짜를 입력해주세요.")
        LocalDateTime startDate,

        @NotNull(message = "봉사 종료 날짜를 입력해주세요.")
        LocalDateTime endDate,

        @NotNull(message = "일정 종류를 선택해주세요. 정기 or 비정기")
        ScheduleType scheduleType,

        @NotBlank(message = "일정 상세를 입력해주세요. 예) 매주 월요일, 화목")
        String scheduleDetails,

        @NotNull(message = "지역을 선택해주세요.")
        District district,

        @NotBlank(message = "상세 내용을 입력해주세요.")
        String content,

        PostType postType,

        @NotNull(message = "장애 종류를 선택해주세요.")
        DisabilityType disabilityType,

        @NotNull(message = "성별을 선택해주세요.")
        Gender gender,

        @NotNull(message = "나이를 입력해주세요.")
        Integer age,

        @NotNull(message = "봉사 시작 시간을 입력해주세요.")
        LocalDateTime assistanceStartTime,

        @NotNull(message = "봉사 종료 시간을 입력해주세요.")
        LocalDateTime assistanceEndTime,

        @NotNull(message = "모집 인원을 입력해주세요.")
        Integer headcount
) {

    public Post toEntity(Member author) {
        return Post.builder()
                .author(author)
                .title(title)
                .assistanceType(assistanceType)
                .schedule(Schedule.builder()
                        .startDate(startDate)
                        .endDate(endDate)
                        .scheduleType(scheduleType)
                        .scheduleDetails(scheduleDetails)
                        .build())
                .district(district)
                .content(content)
                .postType(postType)
                .postStatus(PostStatus.RECRUITING)
                .disabilityType(disabilityType)
                .gender(gender)
                .age(age)
                .assistanceTime(AssistanceTime.builder()
                        .assistanceStartTime(assistanceStartTime)
                        .assistanceEndTime(assistanceEndTime)
                        .build())
                .headcount(headcount)
                .build();
    }
}
