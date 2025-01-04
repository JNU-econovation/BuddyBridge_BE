package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessagesWithCursor;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.service.NotificationService;
import econo.buddybridge.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingRoomService {

    private final MemberService memberService;
    private final ChatMessageRepository chatMessageRepository;
    private final MatchingRepository matchingRepository;
    private final MatchingService matchingService;
    private final NotificationService notificationService;

    @Transactional(readOnly = true) // 매칭방 조회
    public MatchingCustomPage getMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus) {
        PageRequest page = PageRequest.of(0, size);
        return matchingRepository.findMatchings(memberId, size, cursor, matchingStatus, page);
    }

    @Transactional // 메시지 조회
    public ChatMessageCustomPage getMatchingRoomMessages(Long memberId, Long matchingId, Integer size, Long cursor) {
        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);
        Member receiver = getReceiver(matching, memberId);

        matching.validateParticipants(receiver);

        if (cursor == null) {   // 첫 조회 시에 알림 읽음 처리
            notificationService.markAsReadByMatchingRoom(memberId, matchingId); // 해당 매칭방의 알림을 읽음 처리
        }

        ChatMessagesWithCursor chatMessagesWithCursor = chatMessageRepository.findByMatching(matching, cursor, PageRequest.of(0, size));

        List<ChatMessageResDto> chatMessageResDtos = chatMessagesWithCursor.chatMessages();
        Long nextCursor = chatMessagesWithCursor.cursor();
        boolean nextPage = chatMessagesWithCursor.nextPage();

        Post post = matching.getPost();
        ReceiverDto receiverDto = ReceiverDto.from(receiver);
        return ChatMessageCustomPage.of(post, receiverDto, chatMessageResDtos, nextCursor, nextPage);
    }

    private Member getReceiver(Matching matching, Long memberId) {
        Long receiverId;
        if (matching.getGiver().getId().equals(memberId)) {
            receiverId = matching.getTaker().getId();
        } else {
            receiverId = matching.getGiver().getId();
        }

        return memberService.findMemberByIdOrThrow(receiverId);
    }
}
