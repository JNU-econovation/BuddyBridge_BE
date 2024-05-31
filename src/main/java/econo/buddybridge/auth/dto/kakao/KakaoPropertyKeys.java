package econo.buddybridge.auth.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoPropertyKeys {
    private Set<String> keys = new HashSet<>();

    public void addKeysFromClass(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonProperty.class)) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                keys.add(annotation.value());
            }
        }
    }

    public String getPropertyKeysString() {
        return keys.stream()
                .map(key -> String.format("\"kakao_account.%s\"", key))
                .collect(Collectors.joining(",", "[", "]"));
    }
}
