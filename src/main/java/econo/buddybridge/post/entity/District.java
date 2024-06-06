package econo.buddybridge.post.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum District {
    GWANGJU("광주광역시"),
    BUK_GU("광주광역시 북구"),
    NAM_GU("광주광역시 남구"),
    SEO_GU("광주광역시 서구"),
    DONG_GU("광주광역시 동구"),
    GWANGSAN_GU("광주광역시 광산구");

    private final String district;

    District(String district){
        this.district = district;
    }
    @JsonValue
    public String getDistrict(){ return district; }

    @JsonCreator
    public static District fromString(String district){
        return Arrays.stream(District.values())
                .filter(val -> val.getDistrict().equals(district))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(district + "와/과 일치하는 타입이 없습니다."));
    }
}
