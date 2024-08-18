package econo.buddybridge.notification.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.notification.dto.NotificationCustomPage;
import econo.buddybridge.notification.entity.Notification;
import econo.buddybridge.notification.repository.NotificationRepository;
import econo.buddybridge.notification.repository.NotificationRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MemberService memberService;
    private final NotificationRepository notificationRepository;
    private final NotificationRepositoryCustom notificationRepositoryCustom;

    @Transactional
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public NotificationCustomPage getNotifications(Long memberId, Integer size, Long cursor) {
        Member member = memberService.findMemberByIdOrThrow(memberId);
        return notificationRepositoryCustom.findByMemberId(member.getId(), size, cursor);
    }

    @Transactional
    public void markAsRead(Long notificationId, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));

        if (!notification.getReceiver().getId().equals(member.getId())) {
            throw new IllegalArgumentException("본인의 알림만 읽을 수 있습니다.");
        }

        notification.markAsRead();
    }
}
