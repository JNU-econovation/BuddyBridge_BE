package econo.buddybridge.matching.service;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.chat.chatmessage.repository.ChatMessageRepository;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.exception.MatchingUnauthorizedAccessException;
import econo.buddybridge.matching.repository.MatchingRepositoryCustom;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.service.NotificationService;
import econo.buddybridge.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingRoomService {

    private final MemberService memberService;
    private final ChatMessageRepository chatMessageRepository;
    private final MatchingRepositoryCustom matchingRepositoryCustom;
    private final MatchingService matchingService;
    private final NotificationService notificationService;

    @Transactional
    public MatchingCustomPage getMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus) {
        PageRequest page = PageRequest.of(0, size);
        return matchingRepositoryCustom.findMatchings(memberId, size, cursor, matchingStatus, page);
    }

    @Transactional // 메시지 조회
    public ChatMessageCustomPage getMatchingRoomMessages(Long memberId, Long matchingId, Integer size, Long cursor) {

        // 사용자 확인 // TODO: 예외처리 필요, 사용자가 매칭방에 속해있지 않을 경우 500 발생
        Matching matching = matchingService.findMatchingByIdOrThrow(matchingId);

        if (!matching.getGiver().getId().equals(memberId) && !matching.getTaker().getId().equals(memberId)) {
            throw MatchingUnauthorizedAccessException.EXCEPTION;
        }

        Pageable pageable = PageRequest.of(0, size + 1);
        Slice<ChatMessage> chatMessagesSlice;

        if (cursor == null) {
            chatMessagesSlice = chatMessageRepository.findByMatchingId(matchingId, pageable);
        } else {
            chatMessagesSlice = chatMessageRepository.findByMatchingIdAndIdGreaterThan(matchingId, cursor, pageable);
        }

        notificationService.markAsReadByMatchingRoom(memberId, matchingId); // 해당 매칭방의 알림을 읽음 처리

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
        if (matching.getGiver().getId().equals(memberId)) {
            receiverId = matching.getTaker().getId();
        } else {
            receiverId = matching.getGiver().getId();
        }

        return memberService.findMemberByIdOrThrow(receiverId);
    }
}
