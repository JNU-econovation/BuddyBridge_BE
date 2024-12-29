package econo.buddybridge.chat.chatmessage.entity;

import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "MESSAGE_READ_STATUS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageReadStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    private Matching matching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member reader;

    private LocalDateTime lastReadTime;

    public void updateLastReadTime() {
        this.lastReadTime = LocalDateTime.now();
    }

    private MessageReadStatus(Matching matching, Member reader, LocalDateTime lastReadTime) {
        this.matching = matching;
        this.reader = reader;
        this.lastReadTime = lastReadTime;
    }

    public static MessageReadStatus of(Matching matching, Member reader, LocalDateTime lastReadTime) {
        return new MessageReadStatus(matching, reader, lastReadTime);
    }
}
