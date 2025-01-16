package econo.buddybridge.member.event.handler;

import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatmessage.repository.MessageReadStatusRepository;
import econo.buddybridge.comment.repository.CommentRepository;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.event.MemberDeleteEvent;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.notification.repository.NotificationRepository;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.repository.PostLikeRepository;
import econo.buddybridge.post.repository.PostRepository;
import econo.buddybridge.report.repository.CommentReportRepository;
import econo.buddybridge.report.repository.ReportRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberDeleteEventHandler {

    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final MatchingRepository matchingRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final NotificationRepository notificationRepository;
    private final CommentReportRepository commentReportRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleMemberDeleteEvent(MemberDeleteEvent event) {
        List<Member> members = event.getMembers();

        deleteAboutMatching(members);   // 매칭 관련 삭제

        deleteAboutPost(members);    // 게시글 관련 삭제

        notificationRepository.deleteAllByMemberIn(members);    // 회원이 받은 알림 삭제

        memberRepository.deleteAllIn(members);  // 회원 삭제
    }

    private void deleteAboutMatching(List<Member> members) {
        List<Matching> matchings = matchingRepository.findByMemberIn(members);  // 회원이 참여한 매칭 조회

        chatMessageRepository.deleteAllByMatchingIn(matchings); // 채팅 메시지 삭제
        messageReadStatusRepository.deleteAllByMatchingIn(matchings);   // 읽음 표시 삭제
        reportRepository.deleteAllByMemberIn(members);  // 신고 내역 삭제

        matchingRepository.deleteAllIn(matchings); // 회원이 참여한 매칭 삭제
    }

    private void deleteAboutPost(List<Member> members) {
        List<Post> posts = postRepository.findByMemberIn(members);  // 회원이 작성한 게시글 조회

        commentReportRepository.deleteAllByPostIn(posts); // 회원이 작성한 게시글의 댓글 신고 삭제
        commentRepository.deleteAllByPostIn(posts);     // 회원이 작성한 게시글의 댓글 삭제
        commentRepository.deleteAllByMemberIn(members); // 회원이 작성한 댓글 삭제

        postLikeRepository.deleteAllByPostIn(posts);     // 회원이 작성한 게시글의 좋아요 삭제
        postLikeRepository.deleteAllByMemberIn(members); // 회원이 누른 좋아요 삭제

        postRepository.deleteAllIn(posts);                // 회원이 작성한 게시글 삭제
    }
}
