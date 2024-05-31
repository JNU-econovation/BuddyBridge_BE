package econo.buddybridge.member.entity;

import econo.buddybridge.common.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String phone;

    @Enumerated(EnumType.STRING)
    private DisabilityType disabilityType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder
    public Member(String name, String nickname, String profileImageUrl, String email,
            Integer age, String phone, DisabilityType disabilityType, Gender gender) {
        this.name = name;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.disabilityType = disabilityType;
        this.gender = gender;
    }
}
