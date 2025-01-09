package econo.buddybridge.certification.validator;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.certification.entity.VolunteerCertification;
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

    public void validateVolunteerCertificationAuthor(Member author, Matching matching) {
        // 봉사자(인증 폼 작성자) 정보 확인 (인증 폼 작성자 == 매칭의 giver 인지)
        validateVolunteer(matching, author);
    }

    public void validateVolunteerCertification(Matching matching, Post post, Member member, VolunteerCertificationRequest request) {
        // VOLUNTEERING_COMPLETED
        validateMatchingStatusCompleted(matching);

        // 봉사자 정보 확인 (봉사자 == 매칭의 giver 인지)
        validateVolunteer(matching, member);

        // postType 확인
        validatePostType(post, PostType.fromValue(request.postType()));

        // ScheduleDate 확인(봉사 일자가 게시글의 기간에 포함되는지)
        validateScheduleDate(post, request.volunteerDate());

        // AssistanceType 확인
        validateAssistanceType(post, AssistanceType.fromValue(request.assistanceType()));

        // AssistanceTime 확인(봉사 시간이 게시글의 시간에 포함되는지)
        validateAssistanceTime(post, request.startTime(), request.endTime());
    }

    public void validateVolunteerCertificationUpdate(VolunteerCertification volunteerCertification, Matching matching,
            Post post, Member author, VolunteerCertificationUpdateRequest request) {
        // 인증 폼의 매칭과 입력 받은 매칭 일치 여부 확인
        volunteerCertification.validateBelongingMatching(matching);

        // 봉사자(인증 폼 작성자) 정보 확인 (인증 폼 작성자 == 매칭의 giver 인지)
        validateVolunteer(matching, author);

        // ScheduleDate 확인(봉사 일자가 게시글의 기간에 포함되는지)
        validateScheduleDate(post, request.volunteerDate());

        // AssistanceTime 확인(봉사 시간이 게시글의 시간에 포함되는지)
        validateAssistanceTime(post, request.startTime(), request.endTime());
    }

    private void validateMatchingStatusCompleted(Matching matching) {
        if (!matching.getMatchingStatus().equals(MatchingStatus.VOLUNTEERING_COMPLETED)) {
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
