package econo.buddybridge.post.entity;

import lombok.Getter;

@Getter
public enum AssistanceType {
    교육("교육"),
    생활("생활");

    private final String assistanceType;

    AssistanceType(String assistanceType) {
        this.assistanceType = assistanceType;
    }
}
