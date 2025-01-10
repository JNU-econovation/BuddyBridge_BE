package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchingRepository extends JpaRepository<Matching, Long>, MatchingRepositoryCustom {

    @Query("SELECT m FROM Matching m JOIN FETCH m.post JOIN FETCH m.giver JOIN FETCH m.taker WHERE m.id = :matchingId")
    Optional<Matching> findByIdWithMembersAndPost(Long matchingId);

    @Query("SELECT m FROM Matching m JOIN FETCH m.giver JOIN FETCH m.taker WHERE m.id = :matchingId")
    Optional<Matching> findByIdWithMembers(@Param("matchingId") Long matchingId);

    @Override
    @Query("SELECT m FROM Matching m WHERE m.id = :matchingId")
    Optional<Matching> findById(@Param("matchingId") Long matchingId);

    @Query("SELECT EXISTS (" +
            "SELECT 1 FROM Matching m " +
            "WHERE m.post =:post " +
            "AND ((m.giver =:firstMember AND m.taker=:secondMember) " +
            "OR (m.giver=:secondMember AND m.taker =:firstMember)))")
    boolean existsByPostAndParticipants(
            @Param("post") Post post,
            @Param("firstMember") Member firstMember,
            @Param("secondMember") Member secondMember
    );

    List<Matching> findByPostIn(List<Post> posts);
}
