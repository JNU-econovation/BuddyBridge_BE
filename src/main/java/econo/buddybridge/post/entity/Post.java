package econo.buddybridge.post.entity;


import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.dto.PostReqDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Matching> matchings = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    private Boolean isLiked;

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