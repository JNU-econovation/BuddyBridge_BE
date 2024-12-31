package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.post.dto.CompletedVolunteerPostPage;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchingRepositoryCustom {

    MatchingCustomPage findMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus, Pageable pageable);

    CompletedVolunteerPostPage findCompletedVolunteerPosts(Member author, Integer page, Integer size, String sort, MemberRole memberRole, Boolean isCompleted);

    List<Matching> getMatchingsByMemberRoleAndStatus(Member author, Integer page, Integer size, String sort, MemberRole memberRole, Boolean isCompleted);

    Long getCompletedVolunteerPostsTotalElements(Member author, MemberRole memberRole, Boolean isCompleted);
}
