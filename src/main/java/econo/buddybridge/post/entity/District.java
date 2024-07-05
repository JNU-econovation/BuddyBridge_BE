package econo.buddybridge.post.entity;

import lombok.Getter;

@Getter
public enum District {
    광주광역시("광주광역시"),
    북구("광주광역시 북구"),
    남구("광주광역시 남구"),
    서구("광주광역시 서구"),
    동구("광주광역시 동구"),
    광산구("광주광역시 광산구");

    private final String district;

    District(String district){
        this.district = district;
    }
}
