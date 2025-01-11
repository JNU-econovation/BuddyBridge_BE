package econo.buddybridge.post.entity;

import econo.buddybridge.common.validation.ValueEnum;
import econo.buddybridge.post.exception.AssistanceTypeInvalidException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum AssistanceType implements ValueEnum<String> {
    학습("학습"),
    식사("식사"),
    이동("이동"),
    기타("기타");

    private final String value;

    AssistanceType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static AssistanceType fromValue(String value) {
        return Arrays.stream(AssistanceType.values())
                .filter(assistanceType -> assistanceType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> AssistanceTypeInvalidException.EXCEPTION);
    }
}
