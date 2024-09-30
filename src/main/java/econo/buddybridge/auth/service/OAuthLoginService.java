package econo.buddybridge.auth.service;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.OAuthLoginParams;
import econo.buddybridge.auth.dto.kakao.UserInfoWithKakaoToken;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final OAuthInfoService OAuthInfoService;
    private final MemberService memberService;

    public MemberResDto login(OAuthLoginParams params) {
        UserInfoWithKakaoToken userInfo = OAuthInfoService.getUserInfo(params);
        return memberService.findOrCreateMemberByEmail(userInfo);
    }

    public void logout(OAuthProvider provider) {
        OAuthInfoService.logout(provider);
    }
}
