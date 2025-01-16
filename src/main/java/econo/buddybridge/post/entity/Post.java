package econo.buddybridge.post.entity;


import econo.buddybridge.certification.exception.VolunteerCertificationAssistanceTimeMismatchException;
import econo.buddybridge.certification.exception.VolunteerCertificationAssistanceTypeMismatchException;
import econo.buddybridge.certification.exception.VolunteerCertificationScheduleDateMismatchException;
import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.exception.CommentSameGenderOnlyException;
import econo.buddybridge.comment.exception.CommentSelfNotAllowedException;
import econo.buddybridge.common.persistence.SoftDeletableEntity;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.dto.PostUpdateReqDto;
import econo.buddybridge.post.exception.PostDeleteNotAllowedException;
import econo.buddybridge.post.exception.PostUnauthorizedAccessException;
import econo.buddybridge.post.exception.PostUpdateNotAllowedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@DynamicUpdate
public class Post extends SoftDeletableEntity {

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

    @Enumerated(EnumType.STRING)
    private DisabilityType disabilityType;

    @Enumerated(EnumType.STRING)
    private Gender gender; // 게시글 - 성별 필드

    private Integer age; // 게시글 - 나이 필드

    @Embedded
    private AssistanceTime assistanceTime; // 게시글 - 봉사 시간(시작 & 종료)

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Matching> matchings = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<PostLike> postLikes = new ArrayList<>();

    public void validateCreateCertification(LocalDate volunteerDate, LocalTime startTime, LocalTime endTime, AssistanceType assistanceType) {
        validateScheduleDate(volunteerDate);
        validateAssistanceTime(startTime, endTime);
        validateAssistanceType(assistanceType);
    }

    public void validateUpdateCertification(LocalDate volunteerDate, LocalTime startTime, LocalTime endTime) {
        validateScheduleDate(volunteerDate);
        validateAssistanceTime(startTime, endTime);
    }

    private void validateScheduleDate(LocalDate volunteerDate) {
        LocalDate startDate = this.schedule.getStartDate().toLocalDate();
        LocalDate endDate = this.schedule.getEndDate().toLocalDate();

        if (volunteerDate.isBefore(startDate) || volunteerDate.isAfter(endDate)) {
            throw VolunteerCertificationScheduleDateMismatchException.EXCEPTION;
        }
    }

    private void validateAssistanceTime(LocalTime startTime, LocalTime endTime) {
        LocalTime assistanceStartTime = this.assistanceTime.getAssistanceStartTime();
        LocalTime assistanceEndTime = this.assistanceTime.getAssistanceEndTime();

        if (startTime.isBefore(assistanceStartTime) || endTime.isAfter(assistanceEndTime)) {
            throw VolunteerCertificationAssistanceTimeMismatchException.EXCEPTION;
        }
    }

    private void validateAssistanceType(AssistanceType assistanceType) {
        if (!this.assistanceType.equals(assistanceType)) {
            throw VolunteerCertificationAssistanceTypeMismatchException.EXCEPTION;
        }
    }

    public void validateAuthor(Member author) {
        if (!this.author.equals(author)) {
            throw PostUnauthorizedAccessException.EXCEPTION;
        }
    }

    public void validateUpdateBy(Member author) {
        if (!this.author.equals(author)) {
            throw PostUpdateNotAllowedException.EXCEPTION;
        }
    }

    public void validateDeletionBy(Member author) {
        if (!this.author.equals(author)) {
            throw PostDeleteNotAllowedException.EXCEPTION;
        }
    }

    public void validateCommentBy(Member author) {
        if (this.author.equals(author)) {
            throw CommentSelfNotAllowedException.EXCEPTION;
        }

        if (this.gender != author.getGender()) {
            throw CommentSameGenderOnlyException.EXCEPTION;
        }
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
                    postUpdateReqDto.assistanceStartTime() != null ? postUpdateReqDto.assistanceStartTime()
                            : this.assistanceTime.getAssistanceStartTime(),
                    postUpdateReqDto.assistanceEndTime() != null ? postUpdateReqDto.assistanceEndTime() : this.assistanceTime.getAssistanceEndTime()
            );
        }

        this.title = postUpdateReqDto.title() != null ? postUpdateReqDto.title() : this.title;
        this.assistanceType = postUpdateReqDto.assistanceType() != null ? AssistanceType.fromValue(postUpdateReqDto.assistanceType()) : this.assistanceType;
        this.schedule = updateSchedule != null ? updateSchedule : this.schedule;
        this.district = postUpdateReqDto.district() != null ? postUpdateReqDto.district() : this.district;
        this.content = postUpdateReqDto.content() != null ? postUpdateReqDto.content() : this.content;
        this.disabilityType = postUpdateReqDto.disabilityType() != null ? DisabilityType.fromValue(postUpdateReqDto.disabilityType()) : this.disabilityType;
        this.gender = postUpdateReqDto.gender() != null ? postUpdateReqDto.gender() : this.gender;
        this.age = postUpdateReqDto.age() != null ? postUpdateReqDto.age() : this.age;
        this.assistanceTime = updateAssistanceTime != null ? updateAssistanceTime : this.assistanceTime;
    }

}
