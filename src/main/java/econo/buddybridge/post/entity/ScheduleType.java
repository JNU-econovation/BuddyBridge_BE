package econo.buddybridge.post.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ScheduleType {
    REGULAR("정기"),
    IRREGULAR("비정기");

    private final String scheduleType;

    ScheduleType(String scheduleType){ this.scheduleType = scheduleType; }

    @JsonValue
    public String getScheduleType(){
        return scheduleType;
    }

    @JsonCreator
    public static ScheduleType fromString(String scheduleType){
        return Arrays.stream(ScheduleType.values())
                .filter(val -> val.getScheduleType().equals(scheduleType))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(scheduleType+"와/과 일치하는 타입이 없습니다."));
    }
}
