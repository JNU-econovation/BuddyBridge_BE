package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.dto.MatchingUpdateDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.exception.MatchingNotFoundException;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.exception.PostUnauthorizedAccessException;
import econo.buddybridge.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final ChatMessageRepository chatMessageRepository;
    private final MatchingRepository matchingRepository;
    private final MemberService memberService;
    private final PostService postService;

    // 존재하는 매칭인지 확인
    @Transactional(readOnly = true)
    public Matching findMatchingByIdOrThrow(Long matchingId) {
        return matchingRepository.findById(matchingId)
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

        Member loginMember = memberService.findMemberByIdOrThrow(memberId);

        Member taker, giver;

        if (post.getPostType() == PostType.GIVER) {
            giver = loginMember;
            taker = memberService.findMemberByIdOrThrow(matchingReqDto.takerId());
        } else {
            giver = memberService.findMemberByIdOrThrow(matchingReqDto.giverId());
            taker = loginMember;
        }

        validatePostAuthor(post, memberId);

        Matching matching = matchingReqToMatching(post, taker, giver);

        chatMessageRepository.save(
                ChatMessage.builder()
                        .matching(matching)
                        .content("매칭이 생성되었습니다. 채팅을 통해 상대방과 연락해보세요!")
                        .messageType(MessageType.INFO)
                        .sender(taker)
                        .build()
        );

        return matchingRepository.save(matching).getId();
    }

    @Transactional // 매칭 업데이트
    public Long updateMatching(Long matchingId, MatchingUpdateDto matchingUpdateDto, Long memberId) {

        Matching matching = findMatchingByIdOrThrow(matchingId);

        validatePostAuthor(matching.getPost(), memberId);

        matching.updateMatching(matchingUpdateDto.matchingStatus());

        return matching.getId();
    }

    @Transactional // 매칭 삭제
    public void deleteMatching(Long matchingId, Long memberId) {
        Matching matching = findMatchingByIdOrThrow(matchingId);
        validatePostAuthor(matching.getPost(), memberId);

        matchingRepository.delete(matching);
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

    // 게시글 작성 회원과 현재 로그인한 회원 일치 여부 판단
    private void validatePostAuthor(Post post, Long memberId) {
        if ((post.getPostType() == PostType.GIVER && !post.getAuthor().getId().equals(memberId)) ||
                (post.getPostType() == PostType.TAKER && !post.getAuthor().getId().equals(memberId))) {
            throw PostUnauthorizedAccessException.EXCEPTION;
        }
    }
}
