package econo.buddybridge.auth.service;

import econo.buddybridge.auth.dto.LoginReqDto;
import econo.buddybridge.auth.jwt.AuthToken;
import econo.buddybridge.auth.jwt.service.AuthTokenService;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.dto.MemberSignUpReqDto;
import econo.buddybridge.member.dto.MemberSignUpResDto;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final AuthTokenService authTokenService;

    @Transactional
    public MemberSignUpResDto signUp(MemberSignUpReqDto memberSignUpReqDto) {
        return memberService.createSignUpMember(memberSignUpReqDto);
    }

    @Transactional
    public AuthToken loginWithToken(LoginReqDto params) {
        MemberResDto member = memberService.findMemberByEmailAndPassword(params);
        return authTokenService.generateAuthToken(member.memberId());
    }

    @Transactional
    public AuthToken reissue(String refreshToken) {
        return authTokenService.reissue(refreshToken);
    }

    @Transactional
    public void logout(Long memberId) {
        authTokenService.logout(memberId);
    }
}
