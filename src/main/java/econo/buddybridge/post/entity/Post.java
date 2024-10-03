package econo.buddybridge.post.entity;


import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.dto.PostUpdateReqDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@DynamicUpdate
@EntityListeners(value = AuditingEntityListener.class)
public class Post extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    private String title;

    @Enumerated(EnumType.STRING)
    private AssistanceType assistanceType;

    @Embedded
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private District district;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Default
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.RECRUITING; // 모집 중, 모집 완료

    @Enumerated(EnumType.STRING)
    private DisabilityType disabilityType;

    @Enumerated(EnumType.STRING)
    private Gender gender; // 게시글 - 성별 필드

    private Integer age; // 게시글 - 나이 필드

    @Embedded
    private AssistanceTime assistanceTime; // 게시글 - 봉사 시간(시작 & 종료)

    private Integer headcount; // 모집 최대 인원

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Matching> matchings = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    public void changeStatus(PostStatus status) { // 상태 변경
        this.postStatus = status;
    }

    public void updatePost(PostUpdateReqDto postUpdateReqDto) {
        Schedule updateSchedule = null;
        AssistanceTime updateAssistanceTime = null;

        if (postUpdateReqDto.startDate() != null || postUpdateReqDto.endDate() != null ||
                postUpdateReqDto.scheduleType() != null || postUpdateReqDto.scheduleDetails() != null) {
            updateSchedule = new Schedule(
                    postUpdateReqDto.startDate() != null ? postUpdateReqDto.startDate() : this.schedule.getStartDate(),
                    postUpdateReqDto.endDate() != null ? postUpdateReqDto.endDate() : this.schedule.getEndDate(),
                    postUpdateReqDto.scheduleType() != null ? postUpdateReqDto.scheduleType() : this.schedule.getScheduleType(),
                    postUpdateReqDto.scheduleDetails() != null ? postUpdateReqDto.scheduleDetails() : this.schedule.getScheduleDetails()
            );
        }

        if (postUpdateReqDto.assistanceStartTime() != null || postUpdateReqDto.assistanceEndTime() != null) {

            updateAssistanceTime = new AssistanceTime(
                    postUpdateReqDto.assistanceStartTime() != null ? postUpdateReqDto.assistanceStartTime() : this.assistanceTime.getAssistanceStartTime(),
                    postUpdateReqDto.assistanceEndTime() != null ? postUpdateReqDto.assistanceEndTime() : this.assistanceTime.getAssistanceEndTime()
            );
        }

        this.title = postUpdateReqDto.title() != null ? postUpdateReqDto.title() : this.title;
        this.assistanceType = postUpdateReqDto.assistanceType() != null ? postUpdateReqDto.assistanceType() : this.assistanceType;
        this.schedule = updateSchedule != null ? updateSchedule : this.schedule;
        this.district = postUpdateReqDto.district() != null ? postUpdateReqDto.district() : this.district;
        this.content = postUpdateReqDto.content() != null ? postUpdateReqDto.content() : this.content;
        this.disabilityType = postUpdateReqDto.disabilityType() != null ? postUpdateReqDto.disabilityType() : this.disabilityType;
        this.gender = postUpdateReqDto.gender() != null ? postUpdateReqDto.gender() : this.gender;
        this.age = postUpdateReqDto.age() != null ? postUpdateReqDto.age() : this.age;
        this.assistanceTime = updateAssistanceTime != null ? updateAssistanceTime : this.assistanceTime;
        this.headcount = postUpdateReqDto.headcount() != null ? postUpdateReqDto.headcount() : this.headcount;
    }

}
