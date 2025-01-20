package econo.buddybridge.comment.service;

import static econo.buddybridge.common.consts.BuddyBridgeStatic.COMMENT_NOTIFICATION_MESSAGE;
import static econo.buddybridge.common.consts.BuddyBridgeStatic.getCommentNotificationUrl;

import econo.buddybridge.comment.dto.AuthorDto;
import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.CommentReqDto;
import econo.buddybridge.comment.dto.CommentResDto;
import econo.buddybridge.comment.dto.MyPageCommentCustomPage;
import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.event.CommentDeleteEvent;
import econo.buddybridge.comment.exception.CommentAlreadyWrittenException;
import econo.buddybridge.comment.exception.CommentNotFoundException;
import econo.buddybridge.comment.exception.CommentUpdateNotAllowedException;
import econo.buddybridge.comment.repository.CommentRepository;
import econo.buddybridge.common.persistence.filter.annotation.SoftDeletableService;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.entity.NotificationType;
import econo.buddybridge.notification.service.EmitterService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

@SoftDeletableService
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final EmitterService emitterService;
    private final ApplicationEventPublisher publisher;

    @Transactional(readOnly = true) // MyPage 댓글 조회
    public MyPageCommentCustomPage getMyPageComments(Long memberId, Integer page, Integer size, String sort, PostType postType) {
        return commentRepository.findByMemberId(memberId, page, size, sort, postType);
    }

    @Transactional(readOnly = true) // 댓글 조회
    public CommentCustomPage getComments(Long postId, Integer size, String order, Long cursor) {
        Post post = postService.findPostByIdOrThrow(postId);
        PageRequest page = PageRequest.of(0, size, Sort.by(Direction.fromString(order), "id"));
        return commentRepository.findByPost(post, cursor, page);
    }

    public CommentResDto toCommentResDto(Comment comment) {
        return CommentResDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .author(AuthorDto.builder()
                        .memberId(comment.getAuthor().getId())
                        .nickname(comment.getAuthor().getNickname())
                        .profileImg(comment.getAuthor().getProfileImageUrl())
                        .gender(comment.getAuthor().getGender())
                        .age(comment.getAuthor().getAge())
                        .build())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Transactional  // 댓글 생성
    public Long createComment(CommentReqDto commentReqDto, Long postId, Long memberId) {
        Member author = memberService.findMemberByIdOrThrow(memberId);
        Post post = postService.findPostByIdOrThrow(postId);

        post.validateCommentBy(author);

        // 기존에 댓글을 작성한 적이 있는지 확인하고 있다면 댓글 작성 불가
        if (commentRepository.existsByPostAndAuthor(post, author)) {
            throw CommentAlreadyWrittenException.EXCEPTION;
        }

        Comment comment = commentReqToComment(commentReqDto, post, author);

        // 게시글 작성자에게 댓글 알림 전송
        sendNotificationToPostAuthor(author, comment, post);

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
        Member author = memberService.findMemberByIdOrThrow(memberId);

        comment.validateDeletionBy(author);

        publisher.publishEvent(CommentDeleteEvent.from(List.of(comment)));
    }

    private Comment findCommentByIdOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }

    @Transactional
    public void deleteComments(List<Long> commentIds, Long memberId) {
        List<Comment> comments = commentRepository.findAllById(commentIds);

        if (comments.size() != commentIds.size()) {
            throw CommentNotFoundException.EXCEPTION;
        }

        Member author = memberService.findMemberByIdOrThrow(memberId);
        comments.forEach(comment -> comment.validateDeletionBy(author));

        publisher.publishEvent(CommentDeleteEvent.from(comments));
    }

    @Transactional(readOnly = true) // 댓글 조회
    public Comment findCommentByIdWithAuthorOrThrow(Long commentId) {
        return commentRepository.findByIdWithAuthor(commentId)
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
