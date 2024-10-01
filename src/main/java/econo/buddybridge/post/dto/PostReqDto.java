package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.Schedule;
import econo.buddybridge.post.entity.ScheduleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostReqDto(
        @NotBlank(message = "제목을 입력해주세요.")
        String title,
        @NotBlank(message = "도움 종류를 선택해주세요. 교육 or 생활")
        AssistanceType assistanceType,
        @NotBlank(message = "봉사 시작 날짜를 입력해주세요.")
        LocalDateTime startTime,
        @NotBlank(message = "봉사 종료 날짜를 입력해주세요.")
        LocalDateTime endTime,
        @NotBlank(message = "일정 종류를 선택해주세요. 정기 or 비정기")
        ScheduleType scheduleType,
        @NotBlank(message = "일정 상세를 입력해주세요. 예) 매주 월요일, 화목")
        String scheduleDetails,
        @NotBlank(message = "지역을 선택해주세요.")
        District district,
        @NotBlank(message = "상세 내용을 입력해주세요.")
        String content,
        PostType postType,
        @NotBlank(message = "장애 종류를 선택해주세요.")
        DisabilityType disabilityType,
        @NotBlank(message = "성별을 선택해주세요.")
        Gender gender,
        @NotBlank(message = "나이를 입력해주세요.")
        Integer age,
        @NotBlank(message = "봉사 시간을 입력해주세요.")
        String assistanceTime
) {

    public Post toEntity(Member author) {
        return Post.builder()
                .author(author)
                .title(title)
                .assistanceType(assistanceType)
                .schedule(Schedule.builder()
                        .startTime(startTime)
                        .endTime(endTime)
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
                .assistanceTime(assistanceTime)
                .build();
    }
}
