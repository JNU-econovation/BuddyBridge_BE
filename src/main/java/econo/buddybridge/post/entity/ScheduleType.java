package econo.buddybridge.post.entity;

import lombok.Getter;

@Getter
public enum ScheduleType {
    REGULAR("정기"),
    IRREGULAR("비정기");

    private final String scheduleType;

    ScheduleType(String scheduleType){ this.scheduleType = scheduleType; }
}
