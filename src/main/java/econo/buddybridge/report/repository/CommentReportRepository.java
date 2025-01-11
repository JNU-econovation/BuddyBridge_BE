package econo.buddybridge.report.repository;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.report.entity.CommentReport;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    boolean existsByReportedCommentAndReporter(Comment comment, Member member);

    boolean existsByReportedComment(Comment reportedComment);

    @Query("SELECT cr.reportedComment.post FROM CommentReport cr WHERE cr.reportedComment.post IN :posts")
    Set<Post> findPostByReportedCommentPostIn(List<Post> posts);

    @Query("SELECT cr.reportedComment FROM CommentReport cr WHERE cr.reportedComment IN :comments")
    Set<Comment> findByReportedCommentIn(List<Comment> comments);
}
