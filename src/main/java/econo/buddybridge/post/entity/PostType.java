package econo.buddybridge.post.entity;

import econo.buddybridge.common.validation.ValueEnum;
import econo.buddybridge.post.exception.PostTypeInvalidException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PostType implements ValueEnum<String> {
    TAKER("TAKER"),
    GIVER("GIVER");

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
                .orElseThrow(() -> PostTypeInvalidException.EXCEPTION);
    }
}
