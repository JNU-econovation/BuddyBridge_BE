package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.entity.MessageReadStatus;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatmessage.repository.MessageReadStatusRepository;
import econo.buddybridge.matching.dto.MatchingParticipants;
import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.dto.MatchingUpdateDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.event.MatchingDeleteEvent;
import econo.buddybridge.matching.exception.MatchingCompletedException;
import econo.buddybridge.matching.exception.MatchingNotFoundException;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final ChatMessageRepository chatMessageRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;
    private final MatchingRepository matchingRepository;
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
        if (existsMatchingDone(post)) {
            throw MatchingCompletedException.EXCEPTION;
        }

        Member author = memberService.findMemberByIdOrThrow(memberId);
        post.validateAuthor(author);

        MatchingParticipants participants = resolveParticipants(post, author, matchingReqDto);
        Member taker = participants.taker();
        Member giver = participants.giver();

        Matching matching = matchingReqToMatching(post, taker, giver);
        Matching savedMatching = matchingRepository.save(matching);

        initMessageReadStatus(savedMatching, taker, giver); // 마지막으로 읽은 시간 초기화
        saveFirstChatMessage(matching, author);             // 채팅방 생성 메시지 저장

        return savedMatching.getId();
    }

    private MatchingParticipants resolveParticipants(Post post, Member author, MatchingReqDto matchingReqDto) {
        Member taker;
        Member giver;

        if (post.getPostType() == PostType.GIVER) {
            giver = author;
            taker = memberService.findMemberByIdOrThrow(matchingReqDto.takerId());
        } else {
            giver = memberService.findMemberByIdOrThrow(matchingReqDto.giverId());
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
        Member author = memberService.findMemberByIdOrThrow(memberId);

        post.validateAuthor(author);

        MatchingStatus updateStatus = matchingUpdateDto.matchingStatus();

        if (existsMatchingDone(post) && updateStatus == MatchingStatus.DONE) {
            throw MatchingCompletedException.EXCEPTION;
        }

        matching.updateMatchingStatus(updateStatus);
        return matching.getId();
    }

    private boolean existsMatchingDone(Post post) {
        return matchingRepository.findByPostId(post.getId())
                .stream()
                .anyMatch(m -> m.getMatchingStatus() == MatchingStatus.DONE);
    }

    @Transactional // 매칭 삭제
    public void deleteMatching(Long matchingId, Long memberId) {
        Matching matching = findMatchingByIdOrThrow(matchingId);
        Member author = memberService.findMemberByIdOrThrow(memberId);

        matching.getPost().validateAuthor(author);

        publisher.publishEvent(MatchingDeleteEvent.from(matching));
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
