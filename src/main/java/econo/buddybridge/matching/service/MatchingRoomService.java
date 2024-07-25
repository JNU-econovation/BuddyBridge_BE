package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.matching.repository.MatchingRepositoryCustom;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingRoomService {
    
    private final MemberRepository memberRepository;
    private final MatchingRepository matchingRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MatchingRepositoryCustom matchingRepositoryCustom;

    @Transactional
    public MatchingCustomPage getMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus){
        PageRequest page = PageRequest.of(0,size);
        return matchingRepositoryCustom.findMatchings(memberId, size, cursor, matchingStatus, page);
    }

    @Transactional // 메시지 조회
    public ChatMessageCustomPage getMatchingRoomMessages(Long memberId, Long matchingId, Integer size, Long cursor){

        // 사용자 확인 // TODO: 예외처리 필요, 사용자가 매칭방에 속해있지 않을 경우 500 발생
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매칭방입니다."));

        if (!matching.getGiver().getId().equals(memberId) && !matching.getTaker().getId().equals(memberId)){
            throw new IllegalArgumentException("사용자가 매칭방에 속해있지 않습니다.");
        }

        Pageable pageable = PageRequest.of(0, size+1);
        Slice<ChatMessage> chatMessagesSlice;

        if (cursor == null) {
            chatMessagesSlice = chatMessageRepository.findByMatchingId(matchingId, pageable);
        } else {
            chatMessagesSlice = chatMessageRepository.findByMatchingIdAndIdGreaterThan(matchingId, cursor, pageable);
        }

        Post post = matching.getPost();

        Member receiver = getReceiver(matching, memberId);

        ReceiverDto receiverDto = ReceiverDto.builder()
                .receiverId(receiver.getId())
                .receiverName(receiver.getName())
                .receiverProfileImg(receiver.getProfileImageUrl())
                .build();

        List<ChatMessage> chatMessageList = chatMessagesSlice.getContent();

        List<ChatMessageResDto> chatMessageResDtoList = chatMessageList.stream().limit(size)
                .map(chatMessage -> ChatMessageResDto.builder()
                        .messageId(chatMessage.getId())
                        .senderId(chatMessage.getSender().getId())
                        .content(chatMessage.getContent())
                        .messageType(chatMessage.getMessageType())
                        .createdAt(chatMessage.getCreatedAt())
                        .build()).toList();

        boolean nextPage = chatMessageList.size() > size;

        Long nextCursor = nextPage ? chatMessageResDtoList.isEmpty() ? -1L : chatMessageResDtoList.getLast().messageId() : -1L;
        return new ChatMessageCustomPage(post.getPostType(), post.getId(), receiverDto, chatMessageResDtoList, nextCursor, nextPage);
    }

    private Member getReceiver(Matching matching, Long memberId) {
        Long receiverId;
        if (matching.getGiver().getId().equals(memberId)){
            receiverId = matching.getTaker().getId();
        } else {
            receiverId = matching.getGiver().getId();
        }
        
        return memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
