package econo.buddybridge.certification.validator;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class VolunteerCertificationValidator {

    public void validateVolunteerCertification(Matching matching, Post post, Member member, VolunteerCertificationRequest request) {
        AssistanceType assistanceType = AssistanceType.fromValue(request.assistanceType());
        matching.validateCreateVolunteerCertification();
        matching.validateCreateVolunteerer(member);
        post.validateCreateCertification(request.volunteerDate(), request.startTime(), request.endTime(), assistanceType);
    }

    public void validateVolunteerCertificationUpdate(Matching matching, Post post, Member member, VolunteerCertificationUpdateRequest request) {
        matching.validateUpdateVolunteerer(member);
        post.validateUpdateCertification(request.volunteerDate(), request.startTime(), request.endTime());
    }
}