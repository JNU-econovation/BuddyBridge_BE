package econo.buddybridge.certification.service;

import econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.certification.entity.VolunteerCertification;
import econo.buddybridge.certification.exception.VolunteerCertificationNotFoundException;
import econo.buddybridge.certification.mapper.VolunteerCertificationMapper;
import econo.buddybridge.certification.repository.VolunteerCertificationRepository;
import econo.buddybridge.certification.validator.VolunteerCertificationValidator;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VolunteerCertificationService {

    private final VolunteerCertificationRepository volunteerCertificationRepository;
    private final MatchingService matchingService;
    private final MemberService memberService;
    private final VolunteerCertificationValidator volunteerCertificationValidator;

    @Transactional
    public void certify(Long volunteerCertificationId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdOrThrow(volunteerCertificationId);
        volunteerCertification.certify();
    }

    @Transactional(readOnly = true)
    public VolunteerCertificationCustomPage getVolunteerCertificationsForAdmin(Integer page, Integer size, String sort) {
        return volunteerCertificationRepository.findAdminVolunteerCertifications(page, size, sort);
    }

    @Transactional(readOnly = true)
    public AdminVolunteerCertificationDetailResponse getVolunteerCertificationForAdmin(Long volunteerCertificationId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdOrThrow(volunteerCertificationId);
        return volunteerCertificationRepository.findAdminVolunteerCertification(volunteerCertification);
    }

    @Transactional(readOnly = true) // 사용자 조회
    public VolunteerCertificationResponse getVolunteerCertification(Long matchingId, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);
        Matching matching = matchingService.findMatchingByIdOrThrow(matchingId);
        VolunteerCertification volunteerCertification = matching.getVolunteerCertification();
        matching.validateVolunteerer(member);

        return volunteerCertificationRepository.findVolunteerCertificationByMemberAndVolunteerCertification(member, volunteerCertification);
    }

    @Transactional
    public void submitVolunteerCertification(Long matchingId, VolunteerCertificationRequest request, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);

        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);
        Post post = matching.getPost();

        volunteerCertificationValidator.validateVolunteerCertification(matching, post, member, request);

        matching.handleEvent(MatchingStatusChangeEvent.SUBMIT_VOLUNTEERING_VERIFICATION, MemberRole.GIVER);

        volunteerCertificationRepository.save(VolunteerCertification.of(
                matching,
                VolunteerCertificationMapper.toVolunteerTime(request),
                request.content()
        ));
    }

    @Transactional
    public void modifyVolunteerCertification(Long matchingId, VolunteerCertificationUpdateRequest request, Long memberId) {
        Member author = memberService.findMemberByIdOrThrow(memberId);
        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);
        Post post = matching.getPost();

        VolunteerCertification volunteerCertification = matching.getVolunteerCertification();

        volunteerCertificationValidator.validateVolunteerCertificationUpdate(matching, post, author, request);

        volunteerCertification.updateVolunteerCertification(
                VolunteerCertificationMapper.toVolunteerTime(request),
                request.content()
        );
    }

    @Transactional
    public void deleteVolunteerCertificationForAdmin(Long volunteerCertificationId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdOrThrow(volunteerCertificationId);
        volunteerCertificationRepository.delete(volunteerCertification);
    }

    @Transactional(readOnly = true)
    public VolunteerCertification findVolunteerCertificationByIdOrThrow(Long volunteerCertificationId) {
        return volunteerCertificationRepository.findById(volunteerCertificationId)
                .orElseThrow(() -> VolunteerCertificationNotFoundException.EXCEPTION);
    }
}
