package econo.buddybridge.chat.chatmessage.service;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.chat.chatroom.entity.ChatRoom;
import econo.buddybridge.chat.chatroom.repository.ChatRoomRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional // 메시지 저장
    public ChatMessageResDto save(ChatMessageReqDto chatMessageReqDto, Long chatRoomId){
        Member sender = memberRepository.findById(chatMessageReqDto.senderId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(chatMessageReqDto.content())
                .messageType(chatMessageReqDto.messageType())
                .build();

        chatMessageRepository.save(chatMessage);

        chatRoom.updateChatRoom(chatMessage.getContent(),chatMessage.getCreatedAt());

        return ChatMessageResDto.builder()
                .messageId(chatMessage.getId())
                .senderId(chatMessageReqDto.senderId())
                .content(chatMessageReqDto.content())
                .messageType(chatMessageReqDto.messageType())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    @Transactional // 메시지 조회
    public List<ChatMessageResDto> getChatMessages(Long chatRoomId){
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
//                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        List<ChatMessage> chatMessageList = chatMessageRepository.findChatMessagesByChatRoomId(chatRoomId);

        return chatMessageList.stream()
                .map(chatMessage -> ChatMessageResDto.builder()
                        .messageId(chatMessage.getId())
                        .senderId(chatMessage.getSender().getId())
                        .content(chatMessage.getContent())
                        .messageType(chatMessage.getMessageType())
                        .createdAt(chatMessage.getCreatedAt())
                        .build()).toList();
    }

}
