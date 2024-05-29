package econo.buddybridge.post.entity;


import econo.buddybridge.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne()
    @JoinColumn(name="author_id")
    private Member author;

    private String title;

    @Enumerated(EnumType.STRING)
    private AssistanceType assistanceType;

    @Embedded
    private DurationPeriod durationPeriod; // startTime & endTime

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    private String scheduleDetail; // 정기 비정기 옆, 상세 정보 입력

    @Enumerated(EnumType.STRING)
    private District district;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private PostStatus postStatus; // 모집 중, 모집 완료

    protected void setStatus(PostStatus status){ // 상태 변경
        this.postStatus = status;
    }

    @Builder
    public Post(Member author,String title,AssistanceType assistanceType,
                DurationPeriod durationPeriod,ScheduleType scheduleType, String scheduleDetail,
                District district,String content,PostType postType){
        this.author = author;
        this.title = title;
        this.assistanceType = assistanceType;
        this.durationPeriod = durationPeriod;
        this.scheduleType = scheduleType;
        this.scheduleDetail = scheduleDetail;
        this.district = district;
        this.content = content;
        this.postType = postType;
        this.postStatus = PostStatus.RECRUITING;
    }
}
