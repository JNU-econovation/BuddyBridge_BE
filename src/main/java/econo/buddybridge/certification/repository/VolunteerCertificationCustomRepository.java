package econo.buddybridge.certification.repository;

import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;

public interface VolunteerCertificationCustomRepository {

    VolunteerCertificationCustomPage findVolunteerCertifications(Integer page, Integer size, String sort);
}
