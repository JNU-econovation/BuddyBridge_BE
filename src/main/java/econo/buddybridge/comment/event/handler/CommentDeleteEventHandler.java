package econo.buddybridge.comment.event.handler;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.event.CommentDeleteEvent;
import econo.buddybridge.comment.repository.CommentRepository;
import econo.buddybridge.report.repository.CommentReportRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CommentDeleteEventHandler {

    private final CommentReportRepository commentReportRepository;
    private final CommentRepository commentRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleCommentDeleteEvent(CommentDeleteEvent event) {
        List<Comment> comments = event.getComments();

        Set<Comment> reportedComments = commentReportRepository.findByReportedCommentIn(comments);

        if (!reportedComments.isEmpty()) {
            commentRepository.softDeleteAllIn(reportedComments);
        }

        comments = comments.stream()
                .filter(comment -> !reportedComments.contains(comment))
                .toList();

        if (!comments.isEmpty()) {
            commentRepository.deleteAllIn(comments);
        }
    }
}
