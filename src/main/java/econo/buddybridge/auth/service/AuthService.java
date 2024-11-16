package econo.buddybridge.auth.service;

import econo.buddybridge.member.dto.MemberSignUpReqDto;
import econo.buddybridge.member.dto.MemberSignUpResDto;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;

    public MemberSignUpResDto signUp(MemberSignUpReqDto memberSignUpReqDto) {
        return memberService.createSignUpMember(memberSignUpReqDto);
    }
}
