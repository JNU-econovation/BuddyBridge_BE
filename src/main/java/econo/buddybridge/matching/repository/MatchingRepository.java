package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long>, MatchingRepositoryCustom {

    @Query("SELECT m FROM Matching m JOIN FETCH m.post JOIN FETCH m.giver JOIN FETCH m.taker WHERE m.id = :matchingId")
    Optional<Matching> findByIdWithMembersAndPost(Long matchingId);

    List<Matching> findByPostId(Long postId);

    @Query("SELECT m FROM Matching m JOIN FETCH m.giver JOIN FETCH m.taker WHERE m.id = :matchingId")
    Optional<Matching> findByIdWithMembers(@Param("matchingId") Long matchingId);

    @Override
    @Query("SELECT m FROM Matching m WHERE m.id = :matchingId")
    Optional<Matching> findById(@Param("matchingId") Long matchingId);
}
