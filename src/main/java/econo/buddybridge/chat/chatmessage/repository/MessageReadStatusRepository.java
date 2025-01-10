package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.entity.MessageReadStatus;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {

    Optional<MessageReadStatus> findByMatchingAndReader(Matching matching, Member reader);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM MessageReadStatus mrs WHERE mrs.matching IN :matchings")
    void deleteAllByMatchingIn(List<Matching> matchings);
}
