package econo.buddybridge.post.event.handler;

import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.event.PostDeleteEvent;
import econo.buddybridge.post.repository.PostRepository;
import econo.buddybridge.report.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PostDeleteEventHandler {

    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        Post post = event.getPost();

        // 신고된 게시글은 soft delete
        // 신고된 게시글이 아닌 경우 완전 삭제
        if (postReportRepository.existsByReportedPost(post)) {
            post.delete();
        } else {
            postRepository.delete(post);
        }
    }
}
