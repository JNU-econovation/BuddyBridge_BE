package econo.buddybridge.notification.entity;

import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.Member;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "NOTIFICATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String content;

    private String url;

    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id")
    private Member receiver;

    public static Notification createNotification(Member receiver, String content, String url, NotificationType type) {
        Notification notification = new Notification();
        notification.receiver = receiver;
        notification.content = content;
        notification.url = url;
        notification.isRead = false;
        notification.type = type;
        return notification;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
