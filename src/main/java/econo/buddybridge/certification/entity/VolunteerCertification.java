package econo.buddybridge.certification.entity;

import econo.buddybridge.certification.exception.VolunteerCertificationAlreadyCertifiedException;
import econo.buddybridge.certification.exception.VolunteerCertificationMatchingMismatchException;
import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.matching.entity.Matching;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "VOLUNTEER_CERTIFICATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VolunteerCertification extends BaseEntity {

    @Id
    @Column(name = "certification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Matching matching;

    @Embedded
    private VolunteerTime volunteerTime;

    private String content;

    // True: 인증 완료, False: 인증 대기
    // Default: False, 한 번 인증 완료 후 변경 불가
    private boolean isCertified;

    @Builder
    private VolunteerCertification(Matching matching, VolunteerTime volunteerTime, String content, boolean isCertified) {
        this.matching = matching;
        this.volunteerTime = volunteerTime;
        this.content = content;
        this.isCertified = isCertified;
    }

    public static VolunteerCertification of(Matching matching, VolunteerTime volunteerTime, String content) {
        return VolunteerCertification.builder()
                .matching(matching)
                .volunteerTime(volunteerTime)
                .content(content)
                .isCertified(false)
                .build();
    }

    public void updateVolunteerCertification(VolunteerTime volunteerTime, String content) {
        this.volunteerTime = volunteerTime;
        this.content = content;
    }

    public void validateMatching(Matching matching) {
        if (!this.matching.equals(matching)) {
            throw VolunteerCertificationMatchingMismatchException.EXCEPTION;
        }
    }

    public Boolean certify() {
        if (this.isCertified) {
            throw VolunteerCertificationAlreadyCertifiedException.EXCEPTION;
        }
        return this.isCertified = true;
    }
}
