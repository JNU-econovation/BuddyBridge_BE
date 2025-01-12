package econo.buddybridge.common.dto;

import econo.buddybridge.common.exception.PageOrderInvalidTypeException;
import econo.buddybridge.common.validation.ValueEnum;
import java.util.Arrays;

public enum PageOrder implements ValueEnum<String> {
    ASC("asc"),
    DESC("desc");

    private final String value;

    PageOrder(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public static PageOrder fromValue(String value) {
        return Arrays.stream(values())
                .filter(pageOrder -> pageOrder.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> PageOrderInvalidTypeException.EXCEPTION);
    }
}
