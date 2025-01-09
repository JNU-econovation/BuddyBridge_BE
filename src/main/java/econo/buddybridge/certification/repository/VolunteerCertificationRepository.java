package econo.buddybridge.certification.repository;

import econo.buddybridge.certification.dto.detail.CertificationDetailQueryDto;
import econo.buddybridge.certification.entity.VolunteerCertification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerCertificationRepository extends JpaRepository<VolunteerCertification, Long>, VolunteerCertificationCustomRepository {

    @Query("SELECT EXISTS (SELECT 1 FROM VolunteerCertification vc WHERE vc.matching.id = :matchingId)")
    boolean existsByMatchingId(Long matchingId);

    @Query(
            "SELECT new econo.buddybridge.certification.dto.detail.CertificationDetailQueryDto(" +
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
                    "   vc.volunteerInfo.name, " +
                    "   vc.volunteerInfo.email, " +
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
                    "WHERE vc.id = :volunteerCertificationId"
    )
    CertificationDetailQueryDto findCertificationDetailQueryDtoById(@Param("volunteerCertificationId") Long volunteerCertificationId);

    @Query("SELECT vc FROM VolunteerCertification vc JOIN FETCH vc.matching WHERE vc.id = :volunteerCertificationId")
    Optional<VolunteerCertification> findByIdWithMatching(@Param("volunteerCertificationId") Long volunteerCertificationId);
}
