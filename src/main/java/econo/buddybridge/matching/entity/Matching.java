package econo.buddybridge.matching.entity;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import java.util.ArrayList;
import java.util.List;
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
    @JoinColumn(name = "post_id")
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

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Builder
    public Matching(Post post, Member taker, Member giver, MatchingStatus matchingStatus) {
        this.post = post;
        this.taker = taker;
        this.giver = giver;
        this.matchingStatus = matchingStatus;
    }

    // 매칭 상태 변경
    public void updateMatching(MatchingStatus matchingStatus) {
        this.matchingStatus = matchingStatus;
        switch (matchingStatus) {
            case PENDING, FAILED:
                post.changeStatus(PostStatus.RECRUITING);
                break;
            case DONE:
                post.changeStatus(PostStatus.FINISHED);
                break;
        }
    }
}
