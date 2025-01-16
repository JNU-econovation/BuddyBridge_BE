package econo.buddybridge.report.repository;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Report r WHERE r.reporter IN :members OR r.reported IN :members")
    void deleteAllByMemberIn(List<Member> members);
}
