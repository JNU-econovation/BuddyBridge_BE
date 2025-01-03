package econo.buddybridge.member.entity;

import econo.buddybridge.common.validation.ValueEnum;
import econo.buddybridge.member.exception.DisabilityInvalidTypeException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum DisabilityType implements ValueEnum<String> {
    없음("없음"),
    시각장애("시각장애"),
    청각장애("청각장애"),
    지적장애("지적장애"),
    지체장애("지체장애"),
    자폐성장애("자폐성장애"),
    뇌병변장애("뇌병변장애"),
    정신장애("정신장애");

    private final String value;

    DisabilityType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static DisabilityType fromValue(String value) {
        return Arrays.stream(DisabilityType.values())
                .filter(disabilityType -> disabilityType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> DisabilityInvalidTypeException.EXCEPTION);
    }
}
