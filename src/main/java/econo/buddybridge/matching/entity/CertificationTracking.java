package econo.buddybridge.matching.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "CERTIFICATION_TRACKING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertificationTracking {

    @Id
    @Column(name = "certification_tracking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    private Matching matching;

    private LocalDateTime requestedAt;

    @Builder
    private CertificationTracking(Matching matching, LocalDateTime requestedAt) {
        this.matching = matching;
        this.requestedAt = requestedAt;
    }

    public static CertificationTracking of(Matching matching, LocalDateTime requestedAt) {
        return CertificationTracking.builder()
                .matching(matching)
                .requestedAt(requestedAt)
                .build();
    }

    public void validateRequestedAt(LocalDateTime requestedAt) {
        if (!this.requestedAt.plusHours(24).isBefore(requestedAt)) {
            throw new IllegalArgumentException("봉사 인증 요청은 24시간 이후에 가능합니다.");
        }
    }

    public void validateMatchingStatus(MatchingStatus matchingStatus) {
        if (this.getMatching().getMatchingStatus() == MatchingStatus.VOLUNTEERING_COMPLETED) {
            throw new IllegalArgumentException("수혜자가 봉사 완료 인증을 마쳤습니다.");
        }
    }

    public void updateRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }
}
