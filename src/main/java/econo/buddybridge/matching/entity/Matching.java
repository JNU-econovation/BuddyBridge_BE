package econo.buddybridge.matching.entity;

import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "MATCHING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taker_id")
    private Member taker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id")
    private Member giver;

    // 매칭 상태
    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    @Builder
    public Matching(Post post, Member taker, Member giver, MatchingStatus matchingStatus){
        this.post = post;
        this.taker = taker;
        this.giver = giver;
        this.matchingStatus = matchingStatus;
    }
    
    // 매칭 상태 변경
    public void updateMatching(MatchingStatus matchingStatus){
        this.matchingStatus = matchingStatus;
        switch (matchingStatus){
            case PENDING:
                post.changeStatus(PostStatus.RECRUITING);
            case DONE:
                post.changeStatus(PostStatus.FINISHED);
                break;
            case FAILED:
                post.changeStatus(PostStatus.RECRUITING);
                break;
        }
    }
}
