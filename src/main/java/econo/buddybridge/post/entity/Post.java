package econo.buddybridge.post.entity;


import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.dto.PostReqDto;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
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

    public void changeStatus(PostStatus status){ // 상태 변경
        this.postStatus = status;
    }

    public void updatePost(PostReqDto postReqDto){
        Schedule schedule = new Schedule(postReqDto.startTime(), postReqDto.endTime(),
                postReqDto.scheduleType(), postReqDto.scheduleDetails());

        this.title = postReqDto.title();
        this.assistanceType = postReqDto.assistanceType();
        this.schedule = schedule;
        this.district = postReqDto.district();
        this.content = postReqDto.content();
        this.postType = postReqDto.postType();
    }
}
