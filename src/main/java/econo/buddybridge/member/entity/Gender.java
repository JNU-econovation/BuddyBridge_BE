package econo.buddybridge.member.entity;

import lombok.Getter;

@Getter
public enum Gender {
    남자("male"), 여자("female");

    private final String englishName;

    Gender(String englishName) {
        this.englishName = englishName;
    }

    public static Gender fromEnglishName(String englishName) {
        for (Gender gender : Gender.values()) {
            if (gender.getEnglishName().equalsIgnoreCase(englishName)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown gender: " + englishName);
    }
}
