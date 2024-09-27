package econo.buddybridge.chat.chatmessage.service;

import static econo.buddybridge.common.consts.BuddyBridgeStatic.CHAT_NOTIFICATION_MESSAGE;
import static econo.buddybridge.common.consts.BuddyBridgeStatic.CHAT_NOTIFICATION_URL;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.exception.LastChatMessageNotFoundException;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.exception.MatchingUnauthorizedAccessException;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.entity.NotificationType;
import econo.buddybridge.notification.service.EmitterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final MemberService memberService;
    private final ChatMessageRepository chatMessageRepository;
    private final EmitterService emitterService;
    private final MatchingService matchingService;

    @Transactional // 메시지 저장
    public ChatMessageResDto save(Long senderId, ChatMessageReqDto chatMessageReqDto, Long matchingId) {
        Member sender = memberService.findMemberByIdOrThrow(senderId);

        Matching matching = matchingService.findMatchingByIdOrThrow(matchingId);

        ChatMessage chatMessage = ChatMessage.builder()
                .matching(matching)
                .sender(sender)
                .content(chatMessageReqDto.content())
                .messageType(chatMessageReqDto.messageType())
                .build();

        Long receiverId = getReceiverId(sender.getId(), matching.getId());
        Member receiver = memberService.findMemberByIdOrThrow(receiverId);

        emitterService.send(    // 채팅을 받는 사용자에게 알림 전송
                receiver,
                String.format(CHAT_NOTIFICATION_MESSAGE, sender.getName(), chatMessage.getContent()),
                String.format(CHAT_NOTIFICATION_URL, matching.getId()),
                NotificationType.CHAT
        );

        chatMessageRepository.save(chatMessage);

        return ChatMessageResDto.builder()
                .messageId(chatMessage.getId())
                .senderId(chatMessage.getSender().getId())
                .content(chatMessageReqDto.content())
                .messageType(chatMessageReqDto.messageType())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    private Long getReceiverId(Long senderId, Long matchingId) {
        Matching matching = matchingService.findMatchingByIdOrThrow(matchingId);

        if (matching.getGiver().getId().equals(senderId)) {
            return matching.getTaker().getId();
        } else if (matching.getTaker().getId().equals(senderId)) {
            return matching.getGiver().getId();
        } else {
            throw MatchingUnauthorizedAccessException.EXCEPTION;
        }
    }

    @Transactional // 마지막 메시지 조회
    public ChatMessageResDto getLastChatMessage(Long matchingId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findLastMessageByMatchingId(matchingId, PageRequest.of(0, 1));
        if (chatMessageList.isEmpty()) {
            throw LastChatMessageNotFoundException.EXCEPTION;
        }

        ChatMessage chatMessage = chatMessageList.getFirst();

        return ChatMessageResDto.builder()
                .messageId(chatMessage.getId())
                .senderId(chatMessage.getSender().getId()) // senderId 받아오기
                .content(chatMessage.getContent())
                .messageType(chatMessage.getMessageType())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
