package econo.buddybridge.certification.service;

import econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.certification.entity.VolunteerCertification;
import econo.buddybridge.certification.exception.VolunteerCertificationAlreadyExistsException;
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
    public Boolean toggleVolunteerCertification(Long volunteerCertificationId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdOrThrow(volunteerCertificationId);
        return volunteerCertification.toggleCertified();
    }

    @Transactional(readOnly = true)
    public VolunteerCertificationCustomPage getVolunteerCertificationsForAdmin(Integer page, Integer size, String sort) {
        return volunteerCertificationRepository.findAdminVolunteerCertifications(page - 1, size, sort);
    }

    @Transactional(readOnly = true)
    public AdminVolunteerCertificationDetailResponse getVolunteerCertificationForAdmin(Long volunteerCertificationId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdOrThrow(volunteerCertificationId);
        return volunteerCertificationRepository.findAdminVolunteerCertification(volunteerCertification);
    }

    @Transactional(readOnly = true)
    public VolunteerCertificationResponse getVolunteerCertification(Long matchingId, Long certificationId, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdWithMatchingAndPost(certificationId);
        Matching matching = matchingService.findMatchingByIdOrThrow(matchingId);

        volunteerCertification.validateMatching(matching);
        matching.validateVolunteerer(member);

        return volunteerCertificationRepository.findVolunteerCertificationByMemberAndVolunteerCertification(member, volunteerCertification);
    }

    @Transactional
    public void submitVolunteerCertification(Long matchingId, VolunteerCertificationRequest request, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);

        if (volunteerCertificationRepository.existsByMatchingId(matchingId)) {
            throw VolunteerCertificationAlreadyExistsException.EXCEPTION;
        }

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
    public void modifyVolunteerCertification(Long matchingId, Long certificationId, VolunteerCertificationUpdateRequest request, Long memberId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdWithMatchingAndPost(certificationId);
        Member author = memberService.findMemberByIdOrThrow(memberId);
        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);
        Post post = matching.getPost();

        volunteerCertificationValidator.validateVolunteerCertificationUpdate(volunteerCertification, matching, post, author, request);

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
    public VolunteerCertification findVolunteerCertificationByIdWithMatchingAndPost(Long volunteerCertificationId) {
        return volunteerCertificationRepository.findByIdWithMatchingAndPost(volunteerCertificationId)
                .orElseThrow(() -> VolunteerCertificationNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public VolunteerCertification findVolunteerCertificationByIdOrThrow(Long volunteerCertificationId) {
        return volunteerCertificationRepository.findById(volunteerCertificationId)
                .orElseThrow(() -> VolunteerCertificationNotFoundException.EXCEPTION);
    }
}
