package econo.buddybridge.chat.chatroom.service;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatroom.dto.ChatRoomResDto;
import econo.buddybridge.chat.chatroom.entity.ChatRoom;
import econo.buddybridge.chat.chatroom.repository.ChatRoomRepository;
import econo.buddybridge.comment.dto.AuthorDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MatchingRepository matchingRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Transactional(readOnly = true) // 채팅방 조회
    public List<ChatRoomResDto> getChatRoomsByMemberId(Long memberId){

        List<Matching> matchingList = matchingRepository.findMatchingByTakerIdOrGiverId(memberId,memberId);

        return matchingList.stream()
                .map(matching -> {
                    ChatRoom chatRoom = matching.getChatRoom();

                    Member receiver = getReceiver(matching,memberId);

                    ChatMessage lastMessage = chatMessageRepository.findLastMessageByChatRoomId(chatRoom.getId());
                    return ChatRoomResDto.builder()
                            .chatRoomId(chatRoom.getId())
                            .lastMessage(lastMessage.getContent())
                            .lastMessageTime(lastMessage.getCreatedAt())
                            .matchingStatus(matching.getMatchingStatus())
                            .authorDto(AuthorDto.builder() // 상대방으로 고정 - 방법 다양, ex) reveiverId 두기
                                    .memberId(receiver.getId())
                                    .nickname(receiver.getNickname())
                                    .profileImg(receiver.getProfileImageUrl())
                                    .build())
                            .build();
                }).toList();
    }

    // @Transactional(readOnly = true)


    private Member getReceiver(Matching matching, Long memberId) {
        Long receiverId;
        if (matching.getGiver().getId().equals(memberId)) {
            receiverId = matching.getTaker().getId();
        } else {
            receiverId = matching.getGiver().getId();
        }

        return memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }


}
