package econo.buddybridge.chat.chatmessage.service;

import static econo.buddybridge.common.consts.BuddyBridgeStatic.CHAT_NOTIFICATION_MESSAGE;
import static econo.buddybridge.common.consts.BuddyBridgeStatic.CHAT_NOTIFICATION_URL;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.entity.MessageType;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.matching.entity.CertificationTracking;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.exception.MatchingUnauthorizedAccessException;
import econo.buddybridge.matching.repository.CertificationTrackingRepository;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.entity.NotificationType;
import econo.buddybridge.notification.service.EmitterService;
import econo.buddybridge.websocket.WebSocketPrincipal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private static final String SUBSCRIBE_DESTINATION = "/api/queue/chat/";

    private final MemberService memberService;
    private final ChatMessageRepository chatMessageRepository;
    private final EmitterService emitterService;
    private final MatchingService matchingService;
    private final SimpUserRegistry simpUserRegistry;
    private final MessageReadStatusService messageReadStatusService;
    private final CertificationTrackingRepository certificationTrackingRepository;

    @Transactional
    public ChatMessageResDto sendVolunteerCompletionRequest(Long matchingId, Long memberId) {
        Member sender = memberService.findMemberByIdOrThrow(memberId);
        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);

        LocalDateTime requestedAt = LocalDateTime.now();

        certificationTrackingRepository.findByMatchingIdWithMatching(matchingId)
                .ifPresentOrElse(
                        tracking -> validateAndUpdateTracking(tracking, requestedAt),
                        () -> createNewTracking(matching, requestedAt)
                );

        matching.validateMatchingStatusDone(matching.getMatchingStatus());

        Long receiverId = getReceiverId(sender.getId(), matching.getId());
        Member receiver = memberService.findMemberByIdOrThrow(receiverId);

        String content = String.format("%s님이 '봉사 인증 요청'을 보냈습니다.", sender.getName());
        ChatMessage chatMessage = ChatMessage.of(matching, sender, content, MessageType.INFO);
        chatMessageRepository.save(chatMessage);

        sendNotification(receiver, sender, chatMessage, matching);
        updateParticipantsReadStatus(matchingId);   // 읽은 시간 갱신

        return ChatMessageResDto.of(
                chatMessage.getId(),
                chatMessage.getSender().getId(),
                chatMessage.getContent(),
                chatMessage.getMessageType(),
                chatMessage.getCreatedAt()
        );
    }

    private void createNewTracking(Matching matching, LocalDateTime requestedAt) {
        CertificationTracking newTracking = CertificationTracking.of(matching, requestedAt);
        certificationTrackingRepository.save(newTracking);
    }

    private void validateAndUpdateTracking(CertificationTracking tracking, LocalDateTime requestedAt) {
        tracking.validateMatchingStatus(tracking.getMatching().getMatchingStatus());
        tracking.validateRequestedAt(requestedAt);
        tracking.updateRequestedAt(requestedAt);
    }

    @Transactional // 메시지 저장
    public ChatMessageResDto save(Long senderId, ChatMessageReqDto chatMessageReqDto, Long matchingId) {
        Member sender = memberService.findMemberByIdOrThrow(senderId);
        Matching matching = matchingService.findMatchingByIdOrThrow(matchingId);

        Long receiverId = getReceiverId(sender.getId(), matching.getId());
        Member receiver = memberService.findMemberByIdOrThrow(receiverId);

        ChatMessage chatMessage = ChatMessage.of(matching, sender, chatMessageReqDto.content(), chatMessageReqDto.messageType());
        chatMessageRepository.save(chatMessage);

        sendNotification(receiver, sender, chatMessage, matching);
        updateParticipantsReadStatus(matchingId);   // 읽은 시간 갱신

        return ChatMessageResDto.of(
                chatMessage.getId(),
                chatMessage.getSender().getId(),
                chatMessageReqDto.content(),
                chatMessageReqDto.messageType(),
                chatMessage.getCreatedAt()
        );
    }

    // 현재 채팅방(매칭)에 참여중인 사용자들의 읽은 시간을 갱신
    private void updateParticipantsReadStatus(Long matchingId) {
        String destination = SUBSCRIBE_DESTINATION + matchingId;
        simpUserRegistry.findSubscriptions(sub -> sub.getDestination().equals(destination))
                .forEach(sub -> {
                    SimpUser user = sub.getSession().getUser();
                    WebSocketPrincipal principal = (WebSocketPrincipal) user.getPrincipal();
                    messageReadStatusService.updateLastReadTime(matchingId, principal.getSenderId());
                });
    }

    private void sendNotification(Member receiver, Member sender, ChatMessage chatMessage, Matching matching) {
        emitterService.send(    // 채팅을 받는 사용자에게 알림 전송
                receiver,
                String.format(CHAT_NOTIFICATION_MESSAGE, sender.getName(), chatMessage.getContent()),
                String.format(CHAT_NOTIFICATION_URL, matching.getId()),
                NotificationType.CHAT
        );
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
}
