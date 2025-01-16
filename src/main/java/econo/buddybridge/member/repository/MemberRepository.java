package econo.buddybridge.member.repository;

import econo.buddybridge.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    boolean existsByNickname(String nickname);

    boolean existsByNicknameAndIdNot(String nickname, Long id);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    List<Member> findByIdIn(List<Long> memberIds);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Member m WHERE m IN :members")
    void deleteAllIn(List<Member> members);
}
