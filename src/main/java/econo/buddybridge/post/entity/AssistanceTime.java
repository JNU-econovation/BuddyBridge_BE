package econo.buddybridge.post.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class AssistanceTime {

    LocalTime assistanceStartTime;
    LocalTime assistanceEndTime;
}
