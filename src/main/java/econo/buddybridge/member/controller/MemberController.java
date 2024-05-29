package econo.buddybridge.member.controller;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 수정 필요
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @PostMapping()
    public String testMember(@RequestBody Member member){
        memberService.join(member);
        return "create success";
    }
}
