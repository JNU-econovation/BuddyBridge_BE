package econo.buddybridge.certification.service;

import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.certification.dto.detail.CertificationDetailQueryDto;
import econo.buddybridge.certification.dto.detail.CertificationDetailResponse;
import econo.buddybridge.certification.dto.detail.VolunteeringDetailResponse;
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

    @Transactional(readOnly = true)
    public VolunteerCertificationCustomPage getVolunteerCertificationsForAdmin(Integer page, Integer size, String sort) {
        return volunteerCertificationRepository.findVolunteerCertifications(page - 1, size, sort);
    }

    @Transactional(readOnly = true)
    public CertificationDetailResponse getVolunteerCertificationForAdmin(Long certificationId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdOrThrow(certificationId);
        CertificationDetailQueryDto certificationDetailQueryDto = volunteerCertificationRepository.findCertificationDetailQueryDtoByVolunteerCertification(volunteerCertification);
        return certificationDetailQueryDto.toCertificationDetailResponse();
    }

    @Transactional(readOnly = true)
    public VolunteeringDetailResponse getVolunteerCertification(Long certificationId, Long memberId) {
        Member author = memberService.findMemberByIdOrThrow(memberId);
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdWithMatching(certificationId);
        volunteerCertificationValidator.validateVolunteerCertificationAuthor(author, volunteerCertification.getMatching());
        return volunteerCertificationRepository.findVolunteeringDetailResponseByMemberAndCertification(author, volunteerCertification);
    }

    @Transactional
    public void submitVolunteerCertification(Long matchingId, VolunteerCertificationRequest request, Long memberId) {
        if (volunteerCertificationRepository.existsByMatchingId(matchingId)) {
            throw VolunteerCertificationAlreadyExistsException.EXCEPTION;
        }

        Member member = memberService.findMemberByIdOrThrow(memberId);
        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);
        Post post = matching.getPost();

        volunteerCertificationValidator.validateVolunteerCertification(matching, post, member, request);

        matching.handleEvent(MatchingStatusChangeEvent.SUBMIT_VOLUNTEERING_VERIFICATION, MemberRole.GIVER);

        volunteerCertificationRepository.save(VolunteerCertification.of(
                VolunteerCertificationMapper.toVolunteer(request),
                matching,
                VolunteerCertificationMapper.toVolunteerTime(request),
                request.content()
        ));
    }

    @Transactional
    public void modifyVolunteerCertification(Long matchingId, Long certificationId, VolunteerCertificationUpdateRequest request, Long memberId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdWithMatching(certificationId);
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
    public void deleteVolunteerCertification(Long certificationId) {
        VolunteerCertification volunteerCertification = findVolunteerCertificationByIdOrThrow(certificationId);
        volunteerCertificationRepository.delete(volunteerCertification);
    }

    @Transactional(readOnly = true)
    public VolunteerCertification findVolunteerCertificationByIdOrThrow(Long volunteerCertificationId) {
        return volunteerCertificationRepository.findById(volunteerCertificationId)
                .orElseThrow(() -> VolunteerCertificationNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public VolunteerCertification findVolunteerCertificationByIdWithMatching(Long volunteerCertificationId) {
        return volunteerCertificationRepository.findByIdWithMatching(volunteerCertificationId)
                .orElseThrow(() -> VolunteerCertificationNotFoundException.EXCEPTION);
    }
}
