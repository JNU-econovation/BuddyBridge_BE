package econo.buddybridge.certification.repository;

import econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailQueryDto;
import econo.buddybridge.certification.entity.VolunteerCertification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerCertificationRepository extends JpaRepository<VolunteerCertification, Long>, VolunteerCertificationCustomRepository {

    boolean existsByMatchingId(Long matchingId);

    @Query("SELECT vc FROM VolunteerCertification vc JOIN FETCH vc.matching m JOIN FETCH m.post  WHERE vc.id = :volunteerCertificationId")
    Optional<VolunteerCertification> findByIdWithMatchingAndPost(@Param("volunteerCertificationId") Long volunteerCertificationId);

    @Query(
            "SELECT new econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailQueryDto(" +
                    "   a.name, " +
                    "   a.nickname, " +
                    "   a.gender, " +
                    "   a.age, " +
                    "   a.disabilityType, " +
                    "   p.title, " +
                    "   p.district, " +
                    "   p.schedule.startDate, " +
                    "   p.schedule.endDate, " +
                    "   p.schedule.scheduleType, " +
                    "   p.schedule.scheduleDetails, " +
                    "   p.assistanceTime.assistanceStartTime, " +
                    "   p.assistanceTime.assistanceEndTime, " +
                    "   p.content, " +
                    "   vc.id, " +
                    "   m.giver.nickname, " +
                    "   vc.createdAt, " +
                    "   m.giver.name, " +
                    "   m.giver.email, " +
                    "   p.id, " +
                    "   p.postType, " +
                    "   vc.volunteerTime.volunteerDate, " +
                    "   p.assistanceType, " +
                    "   vc.volunteerTime.startTime, " +
                    "   vc.volunteerTime.endTime, " +
                    "   vc.content" +
                    ") " +
                    "FROM VolunteerCertification vc " +
                    "LEFT JOIN vc.matching m " +
                    "LEFT JOIN m.post p " +
                    "LEFT JOIN p.author a " +
                    "WHERE vc = :volunteerCertification"
    )
    AdminVolunteerCertificationDetailQueryDto findAdminVolunteerCertificationDetailQueryDto(@Param("volunteerCertification") VolunteerCertification volunteerCertification);
}
