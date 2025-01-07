package econo.buddybridge.certification.service;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.entity.VolunteerCertification;
import econo.buddybridge.certification.mapper.VolunteerCertificationMapper;
import econo.buddybridge.certification.repository.VolunteerCertificationRepository;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VolunteerCertificationService {

    private final VolunteerCertificationRepository volunteerCertificationRepository;
    private final MatchingService matchingService;
    private final MemberService memberService;

    @Transactional
    public void submitVolunteerForm(Long matchingId, VolunteerCertificationRequest request, Long memberId) {
        Matching matching = matchingService.findByIdWithMembersAndPost(matchingId);
        Post post = matching.getPost();

        // member가 matching에 속해 있는지 giver or taker 사실 무조건 giver여야 함
        Member member = memberService.findMemberByIdOrThrow(memberId);

        // startTime이 endTime보다 빠른지 검증
        if (request.startTime().isAfter(request.endTime())) {
            throw new IllegalArgumentException("봉사 인증 작성 중 문제가 발생했습니다. 봉사 시작 시간이 봉사 종료 시간보다 빠를 수 없습니다.");
        }

        // 게시글 타입 검사 - 받아온 matchingId에 대해 Post를 가져와 Giver인지 Taker인지
        if (!post.getPostType().equals(PostType.fromValue(request.postType()))) {
            throw new IllegalArgumentException("봉사 인증 작성 중 문제가 발생했습니다. 올바른 게시글 타입이 아닙니다. 관리자에게 문의해주세요.");
        }

        // matching.giver의 정보(이름, 이메일) 일치 여부 확인 - member와 giver가 일치하면 되는 거 아닌가?
        Member giver = matching.getGiver();

        if (!(giver.getName().equals(request.giverName())) || !(giver.getEmail().equals(request.giverEmail()))) {
            throw new IllegalArgumentException("봉사 인증 작성 중 문제가 발생했습니다. Giver 정보(이름 또는 이메일)가 일치하지 않습니다. 관리자에게 문의해주세요.");
        }

        // 봉사 일자가 게시글의 일자에 포함되는지 검증
        if (request.volunteerDate().isBefore(post.getSchedule().getStartDate().toLocalDate()) ||
                request.volunteerDate().isAfter(post.getSchedule().getEndDate().toLocalDate())) {
            throw new IllegalArgumentException("봉사 인증 작성 중 문제가 발생했습니다. 봉사 일자가 게시글의 일자에 포함되지 않습니다.");
        }

        // 봉사 시간이 게시글의 시간 범위를 벗어나는지 체크
        if (request.startTime().isBefore(post.getAssistanceTime().getAssistanceStartTime()) ||
                request.endTime().isAfter(post.getAssistanceTime().getAssistanceEndTime())) {
            throw new IllegalArgumentException("봉사 인증 작성 중 문제가 발생했습니다. 입력하신 봉사 시간이 게시글의 시작 및 종료 시간에 포함되지 않습니다.");
        }

        // 도움 유형 검증
        if (!post.getAssistanceType().equals(AssistanceType.fromValue(request.assistanceType()))) {
            throw new IllegalArgumentException("봉사 인증 작성 중 문제가 발생했습니다. 도움 유형이 일치하지 않습니다.");
        }

        volunteerCertificationRepository.save(VolunteerCertification.of(
                VolunteerCertificationMapper.toVolunteer(request),
                matching,
                VolunteerCertificationMapper.toVolunteerTime(request),
                request.content()
        ));
    }
}
