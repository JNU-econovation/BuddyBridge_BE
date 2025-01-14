package econo.buddybridge.matching.entity;

import econo.buddybridge.matching.exception.certification.RequestCoolDownPeriodException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private LocalDateTime requestedAt;

    @Builder
    private CertificationTracking(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public static CertificationTracking of(LocalDateTime requestedAt) {
        return CertificationTracking.builder()
                .requestedAt(requestedAt)
                .build();
    }

    public void updateRequestedAt(LocalDateTime requestedAt) {
        if (!this.requestedAt.plusDays(1).isBefore(requestedAt)) {
            throw RequestCoolDownPeriodException.EXCEPTION;
        }
        this.requestedAt = requestedAt;
    }

    public boolean isRequestedWithinOneDay() {
        LocalDateTime now = LocalDateTime.now();
        return this.requestedAt.plusDays(1).isBefore(now);
    }
}
