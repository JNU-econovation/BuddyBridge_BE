package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import feign.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("SELECT m FROM Matching m " +
            "LEFT JOIN ChatMessage cm ON m.id = cm.matching.id " +
            "WHERE (m.taker.id = :memberId OR m.giver.id = :memberId) " +
            "GROUP BY m.id " +
            "ORDER BY MAX(cm.createdAt) DESC")
    Slice<Matching> findMatchingByTakerIdOrGiverId(@Param("memberId") Long memberId, Pageable pageable);


    @Query("SELECT m FROM Matching m " +
            "LEFT JOIN ChatMessage cm ON m.id = cm.matching.id " +
            "WHERE (m.taker.id = :memberId OR m.giver.id = :memberId) " +
            "AND cm.createdAt > :cursor " +
            "GROUP BY m.id " +
            "ORDER BY MAX(cm.createdAt) DESC")
    Slice<Matching> findMatchingByTakerIdOrGiverIdAndIdLessThan(@Param("memberId") Long memberId,@Param("cursor") LocalDateTime cursor, Pageable pageable);
}
