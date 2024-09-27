package econo.buddybridge.comment.service;

import static econo.buddybridge.common.consts.BuddyBridgeStatic.COMMENT_NOTIFICATION_MESSAGE;
import static econo.buddybridge.common.consts.BuddyBridgeStatic.getCommentNotificationUrl;

import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.CommentReqDto;
import econo.buddybridge.comment.dto.MyPageCommentCustomPage;
import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.exception.CommentDeleteNotAllowedException;
import econo.buddybridge.comment.exception.CommentInvalidDirectionException;
import econo.buddybridge.comment.exception.CommentNotFoundException;
import econo.buddybridge.comment.exception.CommentUpdateNotAllowedException;
import econo.buddybridge.comment.repository.CommentRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.entity.NotificationType;
import econo.buddybridge.notification.service.EmitterService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final EmitterService emitterService;

    @Transactional(readOnly = true) // MyPage 댓글 조회
    public MyPageCommentCustomPage getMyPageComments(Long memberId, Integer page, Integer size, String sort, PostType postType) {
        return commentRepository.findByMemberId(memberId, page - 1, size, sort, postType);
    }

    @Transactional(readOnly = true) // 댓글 조회
    public CommentCustomPage getComments(Long postId, Integer size, String order, Long cursor) {
        Post post = postService.findPostByIdOrThrow(postId);

        Direction direction;
        try {
            direction = Direction.valueOf(order);
        } catch (IllegalArgumentException e) {
            throw CommentInvalidDirectionException.EXCEPTION;
        }

        PageRequest page = PageRequest.of(0, size, Sort.by(direction, "id"));

        return commentRepository.findByPost(post, cursor, page);
    }

    @Transactional  // 댓글 생성
    public Long createComment(CommentReqDto commentReqDto, Long postId, Long memberId) {

        Member member = memberService.findMemberByIdOrThrow(memberId);

        Post post = postService.findPostByIdOrThrow(postId);

        Comment comment = commentReqToComment(commentReqDto, post, member);

        // 게시글 작성자에게 댓글 알림 전송
        sendNotificationToPostAuthor(member, comment, post);

        return commentRepository.save(comment).getId();
    }

    private void sendNotificationToPostAuthor(Member member, Comment comment, Post post) {
        // 알림 내용은 댓글 작성자 이름과 댓글 내용
        String notificationContent = String.format(COMMENT_NOTIFICATION_MESSAGE, member.getName(), comment.getContent());
        String notificationUrl = getCommentNotificationUrl(post.getPostType(), post.getId());

        // 댓글 알림은 게시글 작성자에게 전송
        emitterService.send(post.getAuthor(), notificationContent, notificationUrl, NotificationType.COMMENT);
    }

    @Transactional  // 댓글 수정
    public Long updateComment(Long commentId, CommentReqDto commentReqDto, Long memberId) {
        Comment comment = findCommentByIdOrThrow(commentId);

        if (!comment.getAuthor().getId().equals(memberId)) {
            throw CommentUpdateNotAllowedException.EXCEPTION;
        }

        comment.updateContent(commentReqDto.content());

        return comment.getId();
    }

    @Transactional  // 댓글 삭제
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = findCommentByIdOrThrow(commentId);

        if (!comment.getAuthor().getId().equals(memberId)) {
            throw CommentDeleteNotAllowedException.EXCEPTION;
        }

        commentRepository.delete(comment);
    }

    private Comment findCommentByIdOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }

    private Comment commentReqToComment(CommentReqDto commentReqDto, Post post, Member member) {
        return Comment.builder()
                .post(post)
                .author(member)
                .content(commentReqDto.content())
                .build();
    }
}
