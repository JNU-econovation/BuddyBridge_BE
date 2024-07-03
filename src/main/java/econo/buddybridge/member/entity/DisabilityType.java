package econo.buddybridge.member.entity;

import lombok.Getter;

@Getter
public enum DisabilityType {
    없음("없음"),
    시각장애("시각장애"),
    청각장애("청각장애"),
    지적장애("지적장애"),
    지체장애("지체장애"),
    자폐성장애("자폐성장애"),
    뇌병변장애("뇌병변장애"),
    정신장애("정신장애");

    private final String disabilityName;

    DisabilityType(String disabilityName) {
        this.disabilityName = disabilityName;
    }
}
