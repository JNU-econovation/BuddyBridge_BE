package econo.buddybridge.certification.mapper;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.entity.VolunteerTime;
import econo.buddybridge.certification.entity.Volunteerer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VolunteerCertificationMapper {

    public static Volunteerer toVolunteerer(VolunteerCertificationRequest request) {
        return new Volunteerer(request.volunteerName(), request.volunteerEmail());
    }

    public static VolunteerTime toVolunteerTime(VolunteerCertificationRequest request) {
        return new VolunteerTime(request.volunteerDate(), request.startTime(), request.endTime());
    }
}
