package econo.buddybridge.post.entity;

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

    public String getDistrict(){ return district; }
}
