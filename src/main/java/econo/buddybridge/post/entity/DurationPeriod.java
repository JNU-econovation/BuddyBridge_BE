package econo.buddybridge.post.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DurationPeriod {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
