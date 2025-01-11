package econo.buddybridge.certification.entity;

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

    @Builder
    private VolunteerCertification(Matching matching, VolunteerTime volunteerTime, String content) {
        this.matching = matching;
        this.volunteerTime = volunteerTime;
        this.content = content;
    }

    public static VolunteerCertification of(Matching matching, VolunteerTime volunteerTime, String content) {
        return VolunteerCertification.builder()
                .matching(matching)
                .volunteerTime(volunteerTime)
                .content(content)
                .build();
    }

    public void updateVolunteerCertification(VolunteerTime volunteerTime, String content) {
        this.volunteerTime = volunteerTime;
        this.content = content;
    }
}
