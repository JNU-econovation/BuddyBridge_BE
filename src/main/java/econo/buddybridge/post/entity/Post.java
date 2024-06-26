package econo.buddybridge.post.entity;


import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.dto.PostReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
public class Post extends BaseEntity {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
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

    private PostStatus postStatus; // 모집 중, 모집 완료

    protected void setStatus(PostStatus status){ // 상태 변경
        this.postStatus = status;
    }

    // 업데이트 로직 -> 이렇게 하는게 맞는지
    public void updatePost(Member author, PostReqDto postReqDto){
        Schedule schedule = new Schedule(postReqDto.startTime(),postReqDto.endTime(),
                postReqDto.scheduleType(),postReqDto.scheduleDetails());

        this.author = author;
        this.title = postReqDto.title();
        this.assistanceType = postReqDto.assistanceType();
        this.schedule = schedule;
        this.district = postReqDto.district();
        this.content = postReqDto.content();
        this.postType = postReqDto.postType();
    }

    @Builder
    public Post(Member author,String title,AssistanceType assistanceType,
                Schedule schedule,
                District district,String content,PostType postType){
        this.author = author;
        this.title = title;
        this.assistanceType = assistanceType;
        this.schedule = schedule;
        this.district = district;
        this.content = content;
        this.postType = postType;
        this.postStatus = PostStatus.RECRUITING;
    }
}
