package econo.buddybridge.auth.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;

public interface OAuthInfoResponse {
    String getEmail();
    String getName();
    String getNickname();
    Integer getAge();
    String getGender();
    String getProfileImageUrl();

    default Member toMember() {
        return Member.builder()
                .email(getEmail())
                .name(getName())
                .nickname(getNickname())
                .age(getAge())
                .gender(Gender.fromEnglishName(getGender()))
                .profileImageUrl(getProfileImageUrl())
                .disabilityType(DisabilityType.없음)  // 회원가입 시 초기 장애 유형은 없음으로 설정
                .build();
    }
}
