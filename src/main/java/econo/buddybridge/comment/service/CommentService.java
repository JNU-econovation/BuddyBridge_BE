package econo.buddybridge.comment.service;

import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.CommentReqDto;
import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.repository.CommentRepository;
import econo.buddybridge.comment.repository.CommentRepositoryCustom;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.notification.entity.NotificationType;
import econo.buddybridge.notification.service.EmitterService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentRepositoryCustom commentRepositoryCustom;
    private final EmitterService emitterService;

    @Transactional(readOnly = true) // 댓글 조회
    public CommentCustomPage getComments(Long postId, Integer size, String order, Long cursor) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Direction direction;
        try {
            direction = Direction.valueOf(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 정렬 방식입니다.");
        }

        PageRequest page = PageRequest.of(0, size, Sort.by(direction, "id"));

        return commentRepositoryCustom.findByPost(post, cursor, page);
    }
    
    @Transactional  // 댓글 생성
    public Long createComment(CommentReqDto commentReqDto, Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Comment comment = commentReqToComment(commentReqDto, post, member);

        // 알림 내용은 댓글 작성자 이름과 댓글 내용
        String notificationContent = member.getName() + "님이 댓글을 남겼습니다. - " + comment.getContent();
        String notificationUrl = getCommentNotificationUrl(post.getPostType(), post.getId());

        // 댓글 알림은 게시글 작성자에게 전송
        emitterService.send(post.getAuthor(), notificationContent, notificationUrl, NotificationType.COMMENT);

        return commentRepository.save(comment).getId();
    }

    private String getCommentNotificationUrl(PostType postType, Long postId) {
        switch (postType) {
            case TAKER -> { // 도와줄래요? 게시글
                return "/help-me" + postId;
            }
            case GIVER -> { // 도와줄게요! 게시글
                return "/help-you/" + postId;
            }
        }
        return "";
    }

    @Transactional  // 댓글 수정
    public Long updateComment(Long commentId, CommentReqDto commentReqDto, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!comment.getAuthor().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
        }

        comment.updateContent(commentReqDto.content());

        return comment.getId();
    }

    @Transactional  // 댓글 삭제
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!comment.getAuthor().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    private Comment commentReqToComment(CommentReqDto commentReqDto, Post post, Member member) {
        return Comment.builder()
                .post(post)
                .author(member)
                .content(commentReqDto.content())
                .build();
    }
}
