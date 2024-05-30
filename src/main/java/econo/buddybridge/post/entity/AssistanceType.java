package econo.buddybridge.post.entity;

import lombok.Getter;

@Getter
public enum AssistanceType {
    EDUCATION("교육"),
    LIVING("생활");

    private final String assistanceType;

    AssistanceType(String assistanceType){ this.assistanceType = assistanceType; }
}
