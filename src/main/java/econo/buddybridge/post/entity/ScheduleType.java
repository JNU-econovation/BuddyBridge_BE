package econo.buddybridge.post.entity;

import econo.buddybridge.common.validation.ValueEnum;
import econo.buddybridge.post.exception.ScheduleTypeInvalidException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ScheduleType implements ValueEnum<String> {
    정기("정기"),
    비정기("비정기");

    private final String value;

    ScheduleType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static ScheduleType fromValue(String value) {
        return Arrays.stream(ScheduleType.values())
                .filter(scheduleType -> scheduleType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> ScheduleTypeInvalidException.EXCEPTION);
    }
}
