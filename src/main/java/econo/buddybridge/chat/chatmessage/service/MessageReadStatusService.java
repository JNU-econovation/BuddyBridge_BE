package econo.buddybridge.chat.chatmessage.service;

import econo.buddybridge.chat.chatmessage.entity.MessageReadStatus;
import econo.buddybridge.chat.chatmessage.repository.MessageReadStatusRepository;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageReadStatusService {

    private final MatchingService matchingService;
    private final MemberService memberService;
    private final MessageReadStatusRepository messageReadStatusRepository;

    @Transactional
    public void updateLastReadTime(Long matchingId, Long memberId) {
        Matching matching = matchingService.findMatchingByIdWithMembers(matchingId);
        Member member = memberService.findMemberByIdOrThrow(memberId);

        matching.validateParticipants(member);

        messageReadStatusRepository.findByMatchingAndReader(matching, member)
                .ifPresent(MessageReadStatus::updateLastReadTime);
    }
}
