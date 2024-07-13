package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findMatchingByTakerIdOrGiverId(Long takerId, Long giverId);

}
