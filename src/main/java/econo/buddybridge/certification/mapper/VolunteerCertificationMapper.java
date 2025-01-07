package econo.buddybridge.certification.mapper;

import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.certification.entity.Volunteer;
import econo.buddybridge.certification.entity.VolunteerTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class VolunteerCertificationMapper {

    public static Volunteer toVolunteer(VolunteerCertificationRequest request) {
        return new Volunteer(request.volunteerName(), request.volunteerEmail());
    }

    public static VolunteerTime toVolunteerTime(VolunteerCertificationRequest request) {
        return new VolunteerTime(request.volunteerDate(), request.startTime(), request.endTime());
    }

    public static VolunteerTime toVolunteerTime(VolunteerCertificationUpdateRequest request) {
        return new VolunteerTime(request.volunteerDate(), request.startTime(), request.endTime());
    }
}
