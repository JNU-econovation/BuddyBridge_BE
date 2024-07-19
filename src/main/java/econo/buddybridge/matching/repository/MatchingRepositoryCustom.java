package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface MatchingRepositoryCustom {
    Slice<Matching> findMatchingByTakerIdOrGiverId(Long memberId, Pageable pageable);
    Slice<Matching> findMatchingByTakerIdOrGiverIdAndIdLessThan(Long memberId, LocalDateTime cursor, Pageable pageable);
}
