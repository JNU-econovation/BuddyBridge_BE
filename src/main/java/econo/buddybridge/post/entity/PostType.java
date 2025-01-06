package econo.buddybridge.post.entity;

import econo.buddybridge.common.validation.ValueEnum;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PostType implements ValueEnum<String> {
    TAKER("도와줄래요?"),
    GIVER("도와줄게요!");

    private final String value;

    PostType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static PostType fromValue(String value) {
        return Arrays.stream(PostType.values())
                .filter(postType -> postType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PostType에 해당하는 값이 없습니다."));
    }
}
