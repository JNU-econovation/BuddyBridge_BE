package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.entity.MessageReadStatus;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatmessage.repository.MessageReadStatusRepository;
import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.service.CommentService;
import econo.buddybridge.matching.dto.MatchingParticipants;
import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.dto.MatchingUpdateDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.event.MatchingDeleteEvent;
import econo.buddybridge.matching.exception.CommentNotBelongToMatchingException;
import econo.buddybridge.matching.exception.DuplicateMatchingException;
import econo.buddybridge.matching.exception.MatchingCompletedException;
import econo.buddybridge.matching.exception.MatchingNotFoundException;
import econo.buddybridge.matching.exception.MatchingNotParticipantException;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final ChatMessageRepository chatMessageRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;
    private final MatchingRepository matchingRepository;
    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;
    private final ApplicationEventPublisher publisher;

    // 존재하는 매칭인지 확인
    @Transactional(readOnly = true)
    public Matching findMatchingByIdOrThrow(Long matchingId) {
        return matchingRepository.findById(matchingId)
                .orElseThrow(() -> MatchingNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public Matching findMatchingByIdWithMembers(Long matchingId) {
        return matchingRepository.findByIdWithMembers(matchingId)
                .orElseThrow(() -> MatchingNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public Matching findByIdWithMembersAndPost(Long matchingId) {
        return matchingRepository.findByIdWithMembersAndPost(matchingId)
                .orElseThrow(() -> MatchingNotFoundException.EXCEPTION);
    }

    @Transactional
    public Long createMatchingById(MatchingReqDto matchingReqDto, Long memberId) {
        Post post = postService.findPostByIdOrThrow(matchingReqDto.postId());
        if (matchingRepository.existsCompletedMatchingByPost(post)) {
            throw MatchingCompletedException.EXCEPTION;
        }

        Member author = memberService.findMemberByIdOrThrow(memberId);
        post.validateAuthor(author);

        MatchingParticipants participants = resolveParticipants(post, author, matchingReqDto);
        Member taker = participants.taker();
        Member giver = participants.giver();

        validateDuplicateMatching(post, taker, giver);

        Matching matching = matchingReqToMatching(post, taker, giver);
        Matching savedMatching = matchingRepository.save(matching);

        initMessageReadStatus(savedMatching, taker, giver); // 마지막으로 읽은 시간 초기화
        saveFirstChatMessage(matching, author);             // 채팅방 생성 메시지 저장

        return savedMatching.getId();
    }

    private void validateDuplicateMatching(Post post, Member taker, Member giver) {
        boolean exists = matchingRepository.existsByPostAndParticipants(
                post.getId(),
                taker.getId(),
                giver.getId()
        );

        if (exists) {
            throw DuplicateMatchingException.EXCEPTION;
        }
    }

    private MatchingParticipants resolveParticipants(Post post, Member author, MatchingReqDto matchingReqDto) {
        Member taker;
        Member giver;

        Comment comment = commentService.findCommentByIdWithAuthorOrThrow(matchingReqDto.commentId());

        if (!comment.getPost().getId().equals(post.getId())) {
            throw CommentNotBelongToMatchingException.EXCEPTION;
        }

        Member commentAuthor = comment.getAuthor();

        if (post.getPostType() == PostType.GIVER) {
            giver = author;
            taker = commentAuthor;
        } else {
            giver = commentAuthor;
            taker = author;
        }

        return new MatchingParticipants(taker, giver);
    }

    private void initMessageReadStatus(Matching savedMatching, Member taker, Member giver) {
        LocalDateTime now = LocalDateTime.now();
        messageReadStatusRepository.saveAll(List.of(
                MessageReadStatus.of(savedMatching, taker, now),
                MessageReadStatus.of(savedMatching, giver, now)
        ));
    }

    private void saveFirstChatMessage(Matching matching, Member author) {
        chatMessageRepository.save(
                ChatMessage.of(
                        matching,
                        author,
                        "매칭이 생성되었습니다. 채팅을 통해 상대방과 연락해보세요!",
                        MessageType.INFO
                )
        );
    }

    @Transactional // 매칭 업데이트
    public Long updateMatching(Long matchingId, MatchingUpdateDto matchingUpdateDto, Long memberId) {
        Matching matching = findMatchingByIdOrThrow(matchingId);
        Post post = postService.findPostByIdOrThrow(matching.getPost().getId());
        Member member = memberService.findMemberByIdOrThrow(memberId);
        MemberRole role = getMemberRole(matching, member);

        validateUpdateCondition(matchingUpdateDto, post, member, matching);

        matching.handleEvent(matchingUpdateDto.matchingStatusEvent(), role);

        return matching.getId();
    }

    @Transactional // 매칭 삭제
    public void deleteMatching(Long matchingId, Long memberId) {
        Matching matching = findMatchingByIdOrThrow(matchingId);
        Member author = memberService.findMemberByIdOrThrow(memberId);

        matching.getPost().validateAuthor(author);

        publisher.publishEvent(MatchingDeleteEvent.from(matching));
    }

    private static MemberRole getMemberRole(Matching matching, Member member) {
        MemberRole role;
        if (matching.getTaker().equals(member)) {
            role = MemberRole.TAKER;
        } else if (matching.getGiver().equals(member)) {
            role = MemberRole.GIVER;
        } else {
            throw MatchingNotParticipantException.EXCEPTION;
        }
        return role;
    }

    // 받아온 이벤트를 통해 (매칭 중, 매칭 완료) 변경 시 게시글 작성자 검증
    // 매칭 완료로 변경 시 이미 완료된 매칭이 있는지 검증
    private void validateUpdateCondition(MatchingUpdateDto matchingUpdateDto, Post post, Member member, Matching matching) {
        if (matchingUpdateDto.matchingStatusEvent() == MatchingStatusChangeEvent.TOGGLE_DONE) {
            post.validateAuthor(member);
            if (matchingRepository.existsCompletedMatchingByPost(post) && matching.getMatchingStatus() == MatchingStatus.PENDING) {
                throw MatchingCompletedException.EXCEPTION;
            }
        }
    }

    // MatchingReqDto -> Matching
    private Matching matchingReqToMatching(Post post, Member taker, Member giver) {
        return Matching.builder()
                .post(post)
                .taker(taker)
                .giver(giver)
                .matchingStatus(MatchingStatus.PENDING) // 매칭 생성시 PENDING
                .build();
    }
}
