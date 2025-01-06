package econo.buddybridge.post.entity;

import econo.buddybridge.common.validation.ValueEnum;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum AssistanceType implements ValueEnum<String> {
    학습("학습"),
    식사("식사"),
    이동("이동"),
    기타("기타");

    private final String assistanceType;

    AssistanceType(String assistanceType) {
        this.assistanceType = assistanceType;
    }

    @Override
    public String getValue() {
        return assistanceType;
    }

    public static AssistanceType fromValue(String value) {
        return Arrays.stream(AssistanceType.values())
                .filter(assistanceType -> assistanceType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("AssistanceType에 해당하는 값이 없습니다."));
    }
}
