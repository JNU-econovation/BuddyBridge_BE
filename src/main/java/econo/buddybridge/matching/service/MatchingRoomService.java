package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.dto.MatchingResDto;
import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
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

    // 페이지네이션 적용
    @Transactional
    public MatchingCustomPage getMatchingRoomsByMemberId(Long memberId, Integer size, LocalDateTime cursor){
        Pageable pageable = PageRequest.of(0,size+1);
        Slice<Matching> matchingSlice;

        if(cursor == null){
            matchingSlice = matchingRepository.findMatchingByTakerIdOrGiverId(memberId,pageable);
        } else { // 마지막 채팅 메시지 생성일자 기준 내림차순, cursor 값 이후에 생성된 내용 조회
            matchingSlice = matchingRepository.findMatchingByTakerIdOrGiverIdAndIdLessThan(memberId,cursor,pageable);
        }

        List<Matching> matchingList = matchingSlice.getContent();

        List<MatchingResDto> matchingResDtoList = matchingList.stream().limit(size)
                .map(matching -> {
                    Member receiver = getReceiver(matching,memberId);
                    ChatMessage lastMessage = chatMessageRepository.findLastMessageByMatchingId(matching.getId());
                    return MatchingResDto.builder()
                            .matchingId(matching.getId())
                            .lastMessage(lastMessage.getContent())
                            .lastMessageTime(lastMessage.getCreatedAt())
                            .messageType(lastMessage.getMessageType())
                            .matchingStatus(matching.getMatchingStatus())
                            .receiverDto(
                                    ReceiverDto.builder()
                                            .receiverId(receiver.getId())
                                            .receiverName(receiver.getNickname())
                                            .receiverProfileImg(receiver.getProfileImageUrl())
                                            .build()
                            )
                            .build();
                }).toList();

        boolean nextPage = matchingList.size() > size;

        LocalDateTime nextCursor = nextPage && !matchingResDtoList.isEmpty() ? matchingResDtoList.get(matchingResDtoList.size() - 1).lastMessageTime() : null;
        return new MatchingCustomPage(matchingResDtoList,nextCursor,nextPage);
    }

    @Transactional // 메시지 조회
    public List<ChatMessageResDto> getMatchingRoomMessages(Long matchingId){

        List<ChatMessage> chatMessageList = chatMessageRepository.findChatMessagesByMatchingId(matchingId);

        return chatMessageList.stream()
                .map(chatMessage -> ChatMessageResDto.builder()
                        .messageId(chatMessage.getId())
                        .senderId(chatMessage.getSender().getId())
                        .content(chatMessage.getContent())
                        .messageType(chatMessage.getMessageType())
                        .createdAt(chatMessage.getCreatedAt())
                        .build()).toList();
    }

    private Member getReceiver(Matching matching, Long memberId) {
        Long receiverId;
        if(matching.getGiver().getId().equals(memberId)){
            receiverId = matching.getTaker().getId();
        } else {
            receiverId = matching.getGiver().getId();
        }
        
        return memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
