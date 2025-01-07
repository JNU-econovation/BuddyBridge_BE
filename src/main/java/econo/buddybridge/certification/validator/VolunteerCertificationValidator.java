package econo.buddybridge.certification.validator;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class VolunteerCertificationValidator {

    public void validateVolunteerCertification(Matching matching, Post post, Member member, VolunteerCertificationRequest request) {
        // VOLUNTEERING_COMPLETED, VOLUNTEERING_VERIFIED 상태 확인
        validateMatchingStatus(matching);

        // 봉사자 정보 확인 (이름과 이메일을 검증했는데, 봉사자 = 매칭의 giver 이면됨)
        validateVolunteer(matching, member);

        // postType 확인
        validatePostType(post, PostType.fromValue(request.postType()));

        // Schedule 확인
        validateScheduleDate(post, request.volunteerDate());

        // AssistanceType 확인
        validateAssistanceType(post, AssistanceType.fromValue(request.assistanceType()));

        // AssistanceTime 확인
        validateAssistanceTime(post, request.startTime(), request.endTime());
    }

    private void validateMatchingStatus(Matching matching) {
        if (!matching.getMatchingStatus().equals(MatchingStatus.VOLUNTEERING_COMPLETED) &&
                !matching.getMatchingStatus().equals(MatchingStatus.VOLUNTEERING_VERIFIED)) {
            throw new IllegalArgumentException("봉사 인증 폼 작성 중 문제가 발생했습니다. 매칭 상태가 VC 또는 VV 상태가 아닙니다.");
        }
    }

    private void validateVolunteer(Matching matching, Member member) {
        if (!matching.getGiver().equals(member)) {
            throw new IllegalArgumentException("봉사 인증 폼 작성 중 문제가 발생했습니다. 봉사자가 일치하지 않습니다. 관리자에게 문의해주세요.");
        }
    }

    private void validatePostType(Post post, PostType postType) {
        if (!post.getPostType().equals(postType)) {
            throw new IllegalArgumentException("봉사 인증 폼 작성 중 문제가 발생했습니다. 게시글 유형이 일치하지 않습니다. 관리자에게 문의해주세요.");
        }
    }

    private void validateScheduleDate(Post post, LocalDate volunteerDate) {
        LocalDate startDate = post.getSchedule().getStartDate().toLocalDate();
        LocalDate endDate = post.getSchedule().getEndDate().toLocalDate();

        if (volunteerDate.isBefore(startDate) || volunteerDate.isAfter(endDate)) {
            throw new IllegalArgumentException("봉사 인증 폼 작성 중 문제가 발생했습니다. 봉사 일자가 게시글의 일자에 포함되지 않습니다.");
        }
    }

    private void validateAssistanceTime(Post post, LocalTime startTime, LocalTime endTime) {
        LocalTime assistanceStartTime = post.getAssistanceTime().getAssistanceStartTime();
        LocalTime assistanceEndTime = post.getAssistanceTime().getAssistanceEndTime();

        if (startTime.isBefore(assistanceStartTime) || endTime.isAfter(assistanceEndTime)) {
            throw new IllegalArgumentException("봉사 인증 폼 작성 중 문제가 발생했습니다. 입력하신 봉사 시간이 게시글의 시작 및 종료 시간에 포함되지 않습니다.");
        }
    }

    private void validateAssistanceType(Post post, AssistanceType assistanceType) {
        if (!post.getAssistanceType().equals(assistanceType)) {
            throw new IllegalArgumentException("봉사 인증 폼 작성 중 문제가 발생했습니다. 도움 유형이 일치하지 않습니다.");
        }
    }
}
