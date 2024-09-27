package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("SELECT m FROM Matching m JOIN FETCH m.post JOIN FETCH m.giver JOIN FETCH m.taker WHERE m.id = :matchingId")
    Optional<Matching> findByIdWithMembersAndPost(Long matchingId);
}
