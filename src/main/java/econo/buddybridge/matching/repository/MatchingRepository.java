package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.Matching;
import feign.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

}
