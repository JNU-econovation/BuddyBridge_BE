package econo.buddybridge.member.entity;

import lombok.Getter;

@Getter
public enum DisabilityType {
    VISUAL_IMPAIRMENT("시각장애"),
    HEARING_IMPAIRMENT("청각장애"),
    INTELLECTUAL_DISABILITY("지적장애"),
    PHYSICAL_DISABILITY("지체장애"),
    ASD("자폐성장애"),
    BRAIN_DISABILITY("뇌병변장애"),
    MENTAL_DISABILITY("정신장애"),
    OTHER_DISABILITY("기타");

    public final String disabilityType;

    DisabilityType(String disabilityType){ this.disabilityType = disabilityType; }
}
