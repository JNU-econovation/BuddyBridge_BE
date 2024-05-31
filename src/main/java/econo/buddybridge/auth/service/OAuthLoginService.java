package econo.buddybridge.auth.service;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.OAuthInfoResponse;
import econo.buddybridge.auth.dto.OAuthLoginParams;
import econo.buddybridge.member.dto.MemberDto;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final OAuthInfoService OAuthInfoService;
    private final MemberService memberService;

    public MemberDto login(OAuthLoginParams params) {
        OAuthInfoResponse info = OAuthInfoService.getUserInfo(params);
        return memberService.findOrCreateMemberByEmail(info);
    }

    public void logout(OAuthProvider provider) {
        OAuthInfoService.logout(provider);
    }
}

