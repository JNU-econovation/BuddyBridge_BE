package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.entity.MatchingStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MatchingRepositoryCustom {

    MatchingCustomPage findMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus, Pageable pageable);
}
