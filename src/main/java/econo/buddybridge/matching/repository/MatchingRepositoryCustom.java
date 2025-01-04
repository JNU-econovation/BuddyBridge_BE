package econo.buddybridge.matching.repository;

import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.post.dto.CompletedVolunteerPostPage;
import econo.buddybridge.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface MatchingRepositoryCustom {

    MatchingCustomPage findMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus, Pageable pageable);

    List<Matching> getMatchingsByMemberRoleAndStatus(Member author, Integer page, Integer size, String sort, MemberRole memberRole,
            Boolean isCompleted);

    Long getCompletedVolunteerPostsTotalElements(Member author, MemberRole memberRole, Boolean isCompleted);

    boolean existsCompletedMatchingByPost(Post post);

    CompletedVolunteerPostPage getCompletedVolunteerPosts(Member author, Integer page, Integer size, String sort, MemberRole memberRole,
            Boolean isCompleted);
}
