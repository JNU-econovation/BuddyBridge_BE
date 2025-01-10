package econo.buddybridge.post.event.handler;

import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatmessage.repository.MessageReadStatusRepository;
import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.repository.CommentRepository;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostLike;
import econo.buddybridge.post.event.PostDeleteEvent;
import econo.buddybridge.post.repository.PostLikeRepository;
import econo.buddybridge.post.repository.PostRepository;
import econo.buddybridge.report.repository.PostReportRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PostDeleteEventHandler {

    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MatchingRepository matchingRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;
    private final PostLikeRepository postLikeRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        List<Post> posts = event.getPosts();

        List<Post> reportedPosts = postReportRepository.findByReportedPostIn(posts);
        // 신고된 게시글은 soft delete
        reportedPosts.forEach(Post::delete);

        // 신고된 게시글이 아닌 경우 완전 삭제
        posts = posts.stream()
                .filter(post -> !reportedPosts.contains(post))
                .toList();

        List<Matching> matchings = matchingRepository.findByPostIn(posts);
        chatMessageRepository.deleteAllInBatch(chatMessageRepository.findByMatchingIn(matchings));
        messageReadStatusRepository.deleteAllInBatch(messageReadStatusRepository.findByMatchingIn(matchings));
        matchingRepository.deleteAllInBatch(matchings);

        List<Comment> comments = commentRepository.findByPostIn(posts);
        commentRepository.deleteAllInBatch(comments);

        List<PostLike> postLikes = postLikeRepository.findByPostIn(posts);
        postLikeRepository.deleteAllInBatch(postLikes);

        postRepository.deleteAllInBatch(posts);
    }
}
