package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("SELECT m FROM Matching m JOIN FETCH m.post JOIN FETCH m.giver JOIN FETCH m.taker WHERE m.id = :matchingId")
    Optional<Matching> findByIdWithMembersAndPost(Long matchingId);

    @Query("SELECT COUNT(m) FROM Matching m WHERE m.post.id = :post_id AND m.matchingStatus = 'DONE'")
    Integer countMatchingDoneByPostId(Long post_id);
}
