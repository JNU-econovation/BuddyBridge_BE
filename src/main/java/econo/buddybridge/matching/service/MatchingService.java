package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatroom.entity.ChatRoom;
import econo.buddybridge.chat.chatroom.entity.RoomState;
import econo.buddybridge.chat.chatroom.repository.ChatRoomRepository;
import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingType;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createMatchingById(MatchingReqDto matchingReqDto){

        Post post = postRepository.findById(matchingReqDto.postId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Member taker = memberRepository.findById(matchingReqDto.takerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Member giver = memberRepository.findById(matchingReqDto.giverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // test 데이터
        ChatRoom chatRoom = new ChatRoom(RoomState.ACCEPT,"test", LocalDateTime.now());
        chatRoomRepository.save(chatRoom);

        Matching matching = matchingReqToMatching(post,taker,giver,chatRoom);

        return matchingRepository.save(matching).getId();
    }

    private Matching matchingReqToMatching(Post post,Member taker,Member giver,ChatRoom chatRoom){
        return Matching.builder()
                .post(post)
                .taker(taker)
                .giver(giver)
                .chatRoom(chatRoom)
                .matchingType(MatchingType.PENDING) // 매칭 생성시 PENDING
                .build();
    }

}
