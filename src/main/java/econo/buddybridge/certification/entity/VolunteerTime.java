package econo.buddybridge.certification.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
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
public class VolunteerTime {

    private LocalDate volunteerDate;

    private LocalTime startTime;

    private LocalTime endTime;
}
