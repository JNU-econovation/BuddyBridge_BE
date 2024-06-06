package econo.buddybridge.post.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum AssistanceType {
    EDUCATION("교육"),
    LIVING("생활");

    private final String assistanceType;

    AssistanceType(String assistanceType){ this.assistanceType = assistanceType; }

    @JsonValue
    public String getAssistanceType(){return assistanceType;}
    
    @JsonCreator
    public static AssistanceType fromString(String assistanceType){
        return Arrays.stream(AssistanceType.values())
                .filter(val -> val.getAssistanceType().equals(assistanceType))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(assistanceType + "와/과 일치하는 타입이 없습니다."));
    }
}
