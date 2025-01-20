package econo.buddybridge.post.event.handler;

import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatmessage.repository.MessageReadStatusRepository;
import econo.buddybridge.comment.repository.CommentRepository;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.event.PostDeleteEvent;
import econo.buddybridge.post.repository.PostLikeRepository;
import econo.buddybridge.post.repository.PostRepository;
import econo.buddybridge.report.repository.CommentReportRepository;
import econo.buddybridge.report.repository.MatchingReportRepository;
import econo.buddybridge.report.repository.PostReportRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PostDeleteEventHandler {

    private final PostReportRepository postReportRepository;
    private final CommentReportRepository commentReportRepository;
    private final MatchingReportRepository matchingReportRepository;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MatchingRepository matchingRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;
    private final PostLikeRepository postLikeRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        List<Post> posts = event.getPosts();
        Set<Post> reportedPosts = getReportedPosts(posts);

        // 신고된 게시글은 soft delete
        if (!reportedPosts.isEmpty()) {
            softDeleteAboutPost(reportedPosts);
        }

        posts = posts.stream()  // 신고된 게시글이 아닌 경우만 필터링
                .filter(post -> !reportedPosts.contains(post))
                .toList();

        // 신고된 게시글이 아닌 경우 완전 삭제
        if (!posts.isEmpty()) {
            hardDeleteAboutMatching(posts);
            hardDeleteAboutPost(posts);
        }
    }

    private Set<Post> getReportedPosts(List<Post> posts) {
        Set<Post> reportedPosts = postReportRepository.findByReportedPostIn(posts);
        Set<Post> reportedCommentPosts = commentReportRepository.findPostByReportedCommentPostIn(posts);
        Set<Post> reportedMatchingPosts = matchingReportRepository.findPostByReportedMatchingPostIn(posts);

        reportedPosts.addAll(reportedCommentPosts);
        reportedPosts.addAll(reportedMatchingPosts);
        return reportedPosts;
    }

    private void softDeleteAboutPost(Set<Post> reportedPosts) {
        postRepository.softDeleteAllIn(reportedPosts);
        commentRepository.softDeleteAllByPostIn(reportedPosts);
        matchingRepository.softDeleteAllByPostIn(reportedPosts);
    }

    private void hardDeleteAboutMatching(List<Post> posts) {
        List<Matching> matchings = matchingRepository.findByPostIn(posts);
        chatMessageRepository.deleteAllByMatchingIn(matchings);
        messageReadStatusRepository.deleteAllByMatchingIn(matchings);
        matchingRepository.deleteAllInBatch(matchings);
    }

    private void hardDeleteAboutPost(List<Post> posts) {
        commentRepository.deleteAllByPostIn(posts);
        postLikeRepository.deleteAllByPostIn(posts);
        postRepository.deleteAllIn(posts);
    }
}
