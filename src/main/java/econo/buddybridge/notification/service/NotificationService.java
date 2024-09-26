package econo.buddybridge.notification.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.dto.NotificationCustomPage;
import econo.buddybridge.notification.entity.Notification;
import econo.buddybridge.notification.exception.NotificationAccessDeniedException;
import econo.buddybridge.notification.exception.NotificationNotFoundException;
import econo.buddybridge.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MemberService memberService;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public NotificationCustomPage getNotifications(Long memberId, Integer size, Long cursor, Boolean isRead) {
        Member member = memberService.findMemberByIdOrThrow(memberId);
        return notificationRepository.findByMemberId(member.getId(), size, cursor, isRead);
    }

    @Transactional
    public void markAsRead(Long notificationId, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> NotificationNotFoundException.EXCEPTION);

        if (!notification.getReceiver().getId().equals(member.getId())) {
            throw NotificationAccessDeniedException.EXCEPTION;
        }

        notification.markAsRead();
    }

    @Transactional
    public void markAllAsRead(Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);
        notificationRepository.markAllAsRead(member.getId());
    }

    @Transactional
    public void markAsReadByMatchingRoom(Long memberId, Long matchingId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);
        notificationRepository.markAsReadByMemberIdAndUrl(member.getId(), "/chat/" + matchingId);
    }
}
