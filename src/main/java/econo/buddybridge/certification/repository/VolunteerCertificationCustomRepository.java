package econo.buddybridge.certification.repository;

import econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.entity.VolunteerCertification;

public interface VolunteerCertificationCustomRepository {

    AdminVolunteerCertificationDetailResponse findAdminVolunteerCertification(VolunteerCertification volunteerCertification);

    VolunteerCertificationCustomPage findAdminVolunteerCertifications(Integer page, Integer size, String sort);
}
