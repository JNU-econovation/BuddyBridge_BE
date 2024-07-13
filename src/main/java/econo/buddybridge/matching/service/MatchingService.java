package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatroom.entity.ChatRoom;
import econo.buddybridge.chat.chatroom.repository.ChatRoomRepository;
import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.dto.MatchingUpdateDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final ChatMessageRepository chatMessageRepository;
    private final MatchingRepository matchingRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional // 매칭 생성 -> 예외처리 필요
    public Long createMatchingById(MatchingReqDto matchingReqDto, Long memberId){
        Post post = postRepository.findById(matchingReqDto.postId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Member taker = memberRepository.findById(matchingReqDto.takerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Member giver = memberRepository.findById(matchingReqDto.giverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        validatePostAuthor(post,memberId);
        
        // test 데이터
        ChatRoom chatRoom = new ChatRoom("대화를 시작합니다.", LocalDateTime.now());

        chatRoomRepository.save(chatRoom);
        chatMessageRepository.save(
                ChatMessage.builder()
                        .chatRoom(chatRoom)
                        .content(chatRoom.getLastMessage())
                        .messageType(MessageType.INFO)
                        .sender(taker)
                        .build()
        );

        Matching matching = matchingReqToMatching(post,taker,giver,chatRoom);

        return matchingRepository.save(matching).getId();
    }

    @Transactional // 매칭 업데이트
    public Long updateMatching(Long matchingId, MatchingUpdateDto matchingUpdateDto, Long memberId){
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매칭입니다."));

        validatePostAuthor(matching.getPost(),memberId);

        matching.updateMatching(matchingUpdateDto.matchingStatus());

        return matching.getId();
    }

    @Transactional // 매칭 삭제
    public void deleteMatching(Long matchingId,Long memberId){
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매칭입니다."));

        validatePostAuthor(matching.getPost(),memberId);

        matchingRepository.delete(matching);
    }

    // MatchingReqDto -> Matching
    private Matching matchingReqToMatching(Post post,Member taker,Member giver,ChatRoom chatRoom){
        return Matching.builder()
                .post(post)
                .taker(taker)
                .giver(giver)
                .chatRoom(chatRoom)
                .matchingStatus(MatchingStatus.PENDING) // 매칭 생성시 PENDING
                .build();
    }

    // 게시글 작성 회원과 현재 로그인한 회원 일치 여부 판단
    private void validatePostAuthor(Post post, Long memberId) {
        if ((post.getPostType() == PostType.GIVER && !post.getAuthor().getId().equals(memberId)) ||
                (post.getPostType() == PostType.TAKER && !post.getAuthor().getId().equals(memberId))) {
            throw new IllegalArgumentException("회원님이 작성한 게시글이 아닙니다.");
        }
    }
}
