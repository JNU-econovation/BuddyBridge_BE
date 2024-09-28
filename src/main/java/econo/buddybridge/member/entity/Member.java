package econo.buddybridge.member.entity;

import econo.buddybridge.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickname;

    private String profileImageUrl;

    private String email;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'없음'")
    private DisabilityType disabilityType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String kakaoToken;

    @Builder
    public Member(String name, String nickname, String profileImageUrl, String email,
            Integer age, DisabilityType disabilityType, Gender gender, String kakaoToken) {
        this.name = name;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.age = age;
        this.disabilityType = disabilityType;
        this.gender = gender;
        this.kakaoToken = kakaoToken;
    }

    public void updateKakaoToken(String kakaoToken) {
        this.kakaoToken = kakaoToken;
    }

    public void updateMemberInfo(String name, String nickname, String profileImageUrl, String email, Integer age,
            DisabilityType disabilityType, Gender gender) {
        this.name = name;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.age = age;
        this.disabilityType = disabilityType;
        this.gender = gender;
    }
}
