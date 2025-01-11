package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.entity.CertificationTracking;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CertificationTrackingRepository extends JpaRepository<CertificationTracking, Long> {

    @Query("SELECT ct FROM CertificationTracking ct JOIN FETCH ct.matching m WHERE m.id = :matchingId")
    Optional<CertificationTracking> findByMatchingIdWithMatching(Long matchingId);
}
