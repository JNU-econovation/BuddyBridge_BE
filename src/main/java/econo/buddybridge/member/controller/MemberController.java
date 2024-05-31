package econo.buddybridge.member.controller;

import econo.buddybridge.member.dto.MemberDto;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public MemberDto findMember(Long memberId) {
        return memberService.findMember(memberId);
    }
}
