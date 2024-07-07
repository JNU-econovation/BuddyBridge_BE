package econo.buddybridge.matching.entity;

import econo.buddybridge.chat.chatroom.entity.ChatRoom;
import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
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

    // 채팅방 id 필드
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    // 매칭 상태 -> Boolean이 아닌 EnumType인지 상의 필요(DONE, FAILED, PENDING)
    private Boolean isSuccess;

    @Builder
    public Matching(Post post,Member taker, Member giver,ChatRoom chatRoom,Boolean isSuccess){
        this.post = post;
        this.taker = taker;
        this.giver = giver;
        this.chatRoom = chatRoom;
        this.isSuccess = isSuccess;
    }
    
    // 매칭 상태 변경
    public void updateMatching(Boolean isSuccess){
        this.isSuccess = isSuccess;
    }
}
