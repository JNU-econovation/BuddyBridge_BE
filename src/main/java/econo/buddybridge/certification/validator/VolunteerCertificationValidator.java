package econo.buddybridge.certification.validator;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.exception.VolunteerCertificationAssistanceTimeMismatchException;
import econo.buddybridge.certification.exception.VolunteerCertificationAssistanceTypeMismatchException;
import econo.buddybridge.certification.exception.VolunteerCertificationNotAllowedMatchingStatusException;
import econo.buddybridge.certification.exception.VolunteerCertificationPostTypeMismatchException;
import econo.buddybridge.certification.exception.VolunteerCertificationScheduleDateMismatchException;
import econo.buddybridge.certification.exception.VolunteerCertificationVolunteerMismatchException;
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
        // VOLUNTEERING_COMPLETED, VOLUNTEERING_VERIFIED 상태 확인 Todo: 필요한지 고려
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
            throw VolunteerCertificationNotAllowedMatchingStatusException.EXCEPTION;
        }
    }

    private void validateVolunteer(Matching matching, Member member) {
        if (!matching.getGiver().equals(member)) {
            throw VolunteerCertificationVolunteerMismatchException.EXCEPTION;
        }
    }

    private void validatePostType(Post post, PostType postType) {
        if (!post.getPostType().equals(postType)) {
            throw VolunteerCertificationPostTypeMismatchException.EXCEPTION;
        }
    }

    private void validateScheduleDate(Post post, LocalDate volunteerDate) {
        LocalDate startDate = post.getSchedule().getStartDate().toLocalDate();
        LocalDate endDate = post.getSchedule().getEndDate().toLocalDate();

        if (volunteerDate.isBefore(startDate) || volunteerDate.isAfter(endDate)) {
            throw VolunteerCertificationScheduleDateMismatchException.EXCEPTION;
        }
    }

    private void validateAssistanceTime(Post post, LocalTime startTime, LocalTime endTime) {
        LocalTime assistanceStartTime = post.getAssistanceTime().getAssistanceStartTime();
        LocalTime assistanceEndTime = post.getAssistanceTime().getAssistanceEndTime();

        if (startTime.isBefore(assistanceStartTime) || endTime.isAfter(assistanceEndTime)) {
            throw VolunteerCertificationAssistanceTimeMismatchException.EXCEPTION;
        }
    }

    private void validateAssistanceType(Post post, AssistanceType assistanceType) {
        if (!post.getAssistanceType().equals(assistanceType)) {
            throw VolunteerCertificationAssistanceTypeMismatchException.EXCEPTION;
        }
    }
}
