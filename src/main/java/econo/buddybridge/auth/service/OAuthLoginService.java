package econo.buddybridge.auth.service;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.OAuthLoginParams;
import econo.buddybridge.auth.dto.kakao.UserInfoWithKakaoToken;
import econo.buddybridge.auth.jwt.AuthToken;
import econo.buddybridge.auth.jwt.service.AuthTokenService;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final OAuthInfoService oAuthInfoService;
    private final MemberService memberService;
    private final AuthTokenService authTokenService;

    public MemberResDto login(OAuthLoginParams params) {
        UserInfoWithKakaoToken userInfo = oAuthInfoService.getUserInfo(params);
        return memberService.findOrCreateMemberByEmail(userInfo);
    }

    // JWT를 이용한 로그인
    public AuthToken loginWithToken(OAuthLoginParams params) {
        UserInfoWithKakaoToken userInfo = oAuthInfoService.getUserInfo(params);
        MemberResDto member = memberService.findOrCreateMemberByEmail(userInfo);
        return authTokenService.generateAuthToken(member.memberId());
    }

    public AuthToken reissue(String refreshToken) {
        return authTokenService.reissue(refreshToken);
    }

    public void logout(OAuthProvider provider) {
        oAuthInfoService.logout(provider);
    }
}
