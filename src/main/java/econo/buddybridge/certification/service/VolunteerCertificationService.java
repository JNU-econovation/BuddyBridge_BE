package econo.buddybridge.certification.service;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.entity.VolunteerCertification;
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
    public void submitVolunteerCertification(Long matchingId, VolunteerCertificationRequest request, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);
        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);
        Post post = matching.getPost();

        volunteerCertificationValidator.validateVolunteerCertification(matching, post, member, request);

        // Todo : 하드 코딩된 부분
        // FE에서 요청을 받아서 변경하는게 맞는지, 서버에서 변경하는게 맞는지
        // 현재는 FE에서 아래와 같이 요청 보내면 상태 변경이 되버림 -> FE에서 SUBMIT_VOLUNTEERING_VERIFICATION 요청 못 보내게 설정
        // 봉사 인증 완료도 여러번 글을 쓸 수 있어야 하니깐 -> VC에서 인증폼을 작성하면 DONE으로 변경하는건 어떨까? - 그리고 재 작성 가능하게
        matching.handleEvent(MatchingStatusChangeEvent.SUBMIT_VOLUNTEERING_VERIFICATION, MemberRole.GIVER);

        volunteerCertificationRepository.save(VolunteerCertification.of(
                VolunteerCertificationMapper.toVolunteer(request),
                matching,
                VolunteerCertificationMapper.toVolunteerTime(request),
                request.content()
        ));
    }
}
