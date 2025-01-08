package econo.buddybridge.certification.repository;

import econo.buddybridge.certification.entity.VolunteerCertification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerCertificationRepository extends JpaRepository<VolunteerCertification, Long>, VolunteerCertificationCustomRepository {

    @Query("SELECT vc FROM VolunteerCertification vc JOIN FETCH vc.matching WHERE vc.id = :volunteerCertificationId")
    Optional<VolunteerCertification> findByIdWithMatching(@Param("volunteerCertificationId") Long volunteerCertificationId);
}
