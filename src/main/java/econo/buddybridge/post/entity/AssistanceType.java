package econo.buddybridge.post.entity;

import lombok.Getter;

@Getter
public enum AssistanceType {
    학습("학습"),
    식사("식사"),
    이동("이동");

    private final String assistanceType;

    AssistanceType(String assistanceType) {
        this.assistanceType = assistanceType;
    }
}
