package econo.buddybridge.certification.validator;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.exception.VolunteerCertificationAllowedOnlyVolunteeringCompletedException;
import econo.buddybridge.certification.exception.VolunteerCertificationAssistanceTimeMismatch;
import econo.buddybridge.certification.exception.VolunteerCertificationAssistanceTypeMismatch;
import econo.buddybridge.certification.exception.VolunteerCertificationPostTypeMismatchException;
import econo.buddybridge.certification.exception.VolunteerCertificationScheduleDateMismatch;
import econo.buddybridge.certification.exception.VolunteerCertificationVolunteererMismatchException;
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
        validateVolunteerer(matching, member);

        validateMatchingStatusVolunteeringCompleted(matching);

        validatePostType(post, PostType.fromValue(request.postType()));

        validateScheduleDate(post, request.volunteerDate());

        validateAssistanceType(post, AssistanceType.fromValue(request.assistanceType()));

        validateAssistanceTime(post, request.startTime(), request.endTime());
    }

    private void validateVolunteerer(Matching matching, Member member) {
        if (!matching.getGiver().equals(member)) {
            throw VolunteerCertificationVolunteererMismatchException.EXCEPTION;
        }
    }

    private void validateMatchingStatusVolunteeringCompleted(Matching matching) {
        if (!matching.getMatchingStatus().equals(MatchingStatus.VOLUNTEERING_COMPLETED)) {
            throw VolunteerCertificationAllowedOnlyVolunteeringCompletedException.EXCEPTION;
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
            throw VolunteerCertificationScheduleDateMismatch.EXCEPTION;
        }
    }

    private void validateAssistanceTime(Post post, LocalTime startTime, LocalTime endTime) {
        LocalTime assistanceStartTime = post.getAssistanceTime().getAssistanceStartTime();
        LocalTime assistanceEndTime = post.getAssistanceTime().getAssistanceEndTime();

        if (startTime.isBefore(assistanceStartTime) || endTime.isAfter(assistanceEndTime)) {
            throw VolunteerCertificationAssistanceTimeMismatch.EXCEPTION;
        }
    }

    private void validateAssistanceType(Post post, AssistanceType assistanceType) {
        if (!post.getAssistanceType().equals(assistanceType)) {
            throw VolunteerCertificationAssistanceTypeMismatch.EXCEPTION;
        }
    }
}