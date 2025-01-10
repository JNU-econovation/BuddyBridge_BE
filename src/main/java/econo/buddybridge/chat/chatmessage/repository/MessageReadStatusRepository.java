package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.entity.MessageReadStatus;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {

    Optional<MessageReadStatus> findByMatchingAndReader(Matching matching, Member reader);

    List<MessageReadStatus> findByMatchingIn(List<Matching> matchings);
}
