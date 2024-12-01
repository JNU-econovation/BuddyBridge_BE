package econo.buddybridge.report.repository;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    boolean existsByReportedCommentAndReporter(Comment comment, Member member);

    boolean existsByReportedComment(Comment reportedComment);
}
