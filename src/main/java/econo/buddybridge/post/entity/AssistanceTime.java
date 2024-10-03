package econo.buddybridge.post.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class AssistanceTime {

    LocalDateTime assistanceStartTime;
    LocalDateTime assistanceEndTime;
}
