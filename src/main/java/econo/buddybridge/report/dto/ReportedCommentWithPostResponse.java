package econo.buddybridge.report.dto;

import econo.buddybridge.comment.dto.CommentResDto;
import econo.buddybridge.post.dto.PostDetailDto;

public record ReportedCommentWithPostResponse(
        PostDetailDto post,
        CommentResDto comment
) {

    public static ReportedCommentWithPostResponse of(PostDetailDto post, CommentResDto comment) {
        return new ReportedCommentWithPostResponse(post, comment);
    }
}
