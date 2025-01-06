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
import jakarta.persistence.ManyToOne;
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
    @Column(name = "form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Volunteer volunteerInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Matching matching;

    @Embedded
    private VolunteerTime volunteerTime;

    private String content;

    @Builder
    private VolunteerCertification(Volunteer volunteerInfo, Matching matching, VolunteerTime volunteerTime, String content) {
        this.volunteerInfo = volunteerInfo;
        this.matching = matching;
        this.volunteerTime = volunteerTime;
        this.content = content;
    }

    public static VolunteerCertification of(Volunteer volunteerInfo, Matching matching, VolunteerTime volunteerTime, String content) {
        return VolunteerCertification.builder()
                .volunteerInfo(volunteerInfo)
                .matching(matching)
                .volunteerTime(volunteerTime)
                .content(content)
                .build();
    }
}
