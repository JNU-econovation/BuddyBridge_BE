package econo.buddybridge.notification.repository;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.notification.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Notification n set n.isRead = true where n.receiver.id = :memberId")
    void markAllAsRead(@Param("memberId") Long memberId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Notification n set n.isRead = true where n.receiver.id = :memberId and n.url = :url")
    void markAsReadByMemberIdAndUrl(@Param("memberId") Long memberId, @Param("url") String url);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Notification n WHERE n.receiver IN :members")
    void deleteAllByMemberIn(List<Member> members);
}
