package econo.buddybridge.certification.validator;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.certification.entity.VolunteerCertification;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import org.springframework.stereotype.Component;

@Component
public class VolunteerCertificationValidator {

    public void validateVolunteerCertification(Matching matching, Post post, Member member, VolunteerCertificationRequest request) {
        matching.validateVolunteerer(member);

        matching.validateMatchingStatusVolunteeringCompleted();

        post.validatePostType(PostType.fromValue(request.postType()));

        post.validateScheduleDate(request.volunteerDate());

        post.validateAssistanceType(AssistanceType.fromValue(request.assistanceType()));

        post.validateAssistanceTime(request.startTime(), request.endTime());
    }

    public void validateVolunteerCertificationUpdate(VolunteerCertification volunteerCertification, Matching matching,
            Post post, Member member, VolunteerCertificationUpdateRequest request) {
        volunteerCertification.validateMatching(matching);

        matching.validateVolunteerer(member);

        post.validateScheduleDate(request.volunteerDate());

        post.validateAssistanceTime(request.startTime(), request.endTime());
    }
}