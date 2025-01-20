package econo.buddybridge.blacklist.repository;

import econo.buddybridge.blacklist.entity.BlackList;
import econo.buddybridge.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    void deleteByReportedMember(Member reportedMember);

    boolean existsByReportedMember(Member reportedMember);

    boolean existsByReportedMemberId(Long reportedMemberId);
}
