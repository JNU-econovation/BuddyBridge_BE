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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final AuthTokenService authTokenService;

    public MemberSignUpResDto signUp(MemberSignUpReqDto memberSignUpReqDto) {
        return memberService.createSignUpMember(memberSignUpReqDto);
    }

    public AuthToken loginWithToken(LoginReqDto params) {
        MemberResDto member = memberService.findMemberByEmailAndPassword(params);
        return authTokenService.generateAuthToken(member.memberId());
    }
}
