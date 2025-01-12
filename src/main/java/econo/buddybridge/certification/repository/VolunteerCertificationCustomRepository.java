package econo.buddybridge.certification.repository;

import econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.dto.VolunteerCertificationResponse;
import econo.buddybridge.certification.entity.VolunteerCertification;
import econo.buddybridge.member.entity.Member;

public interface VolunteerCertificationCustomRepository {

    VolunteerCertificationResponse findVolunteerCertificationByMemberAndVolunteerCertification(Member member, VolunteerCertification volunteerCertification);
    
    AdminVolunteerCertificationDetailResponse findAdminVolunteerCertification(VolunteerCertification volunteerCertification);

    VolunteerCertificationCustomPage findAdminVolunteerCertifications(Integer page, Integer size, String sort);
}
