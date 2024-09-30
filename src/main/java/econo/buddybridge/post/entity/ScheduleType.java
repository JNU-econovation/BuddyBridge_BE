package econo.buddybridge.post.entity;

import lombok.Getter;

@Getter
public enum ScheduleType {
    정기("정기"),
    비정기("비정기");

    private final String scheduleType;

    ScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }
}
