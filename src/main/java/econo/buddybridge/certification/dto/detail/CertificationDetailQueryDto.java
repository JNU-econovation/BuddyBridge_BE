package econo.buddybridge.certification.dto.detail;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.ScheduleType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CertificationDetailQueryDto(
        // AuthorDetail 정보
        String authorName,
        String authorNickname,
        Gender authorGender,
        Integer authorAge,
        DisabilityType authorDisabilityType,

        // PostDetail 정보
        String title,
        District district,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType,
        String scheduleDetails,
        LocalTime assistanceStartTime,
        LocalTime assistanceEndTime,
        String postContent,

        // VolunteeringDetail 정보
        Long certificationId,
        String volunteerNickname,
        LocalDateTime certificationCreatedDate,
        String volunteerName,
        String volunteerEmail,
        Long postId,
        PostType postType,
        LocalDate volunteerDate,
        AssistanceType assistanceType,
        LocalTime startTime,
        LocalTime endTime,
        String volunteerContent
) {

    public CertificationDetailResponse toCertificationDetailResponse() {
        return new CertificationDetailResponse(
                toAuthorDetailResponse(this),
                toPostDetailResponse(this),
                toVolunteerCertificationDetailResponse(this)
        );
    }

    private static CertificationPostAuthorDetailResponse toAuthorDetailResponse(CertificationDetailQueryDto dto) {
        return new CertificationPostAuthorDetailResponse(
                dto.authorName,
                dto.authorNickname,
                dto.authorGender,
                dto.authorAge,
                dto.authorDisabilityType
        );
    }

    private static CertificationPostDetailResponse toPostDetailResponse(CertificationDetailQueryDto dto) {
        return new CertificationPostDetailResponse(
                dto.title,
                dto.district,
                dto.startDate,
                dto.endDate,
                dto.scheduleType,
                dto.scheduleDetails,
                dto.assistanceStartTime,
                dto.assistanceEndTime,
                dto.postContent
        );
    }

    private static VolunteeringDetailResponse toVolunteerCertificationDetailResponse(CertificationDetailQueryDto dto) {
        return new VolunteeringDetailResponse(
                dto.certificationId,
                dto.volunteerNickname,
                dto.certificationCreatedDate,
                dto.volunteerName,
                dto.volunteerEmail,
                dto.postId,
                dto.postType,
                dto.volunteerDate,
                dto.assistanceType,
                dto.startTime,
                dto.endTime,
                dto.volunteerContent
        );
    }
}
