package econo.buddybridge.certification.repository;

import econo.buddybridge.certification.entity.VolunteerCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerCertificationRepository extends JpaRepository<VolunteerCertification, Long>, VolunteerCertificationCustomRepository {
    
}
