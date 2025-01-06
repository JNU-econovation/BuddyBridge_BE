package econo.buddybridge.auth.resolver;

import econo.buddybridge.auth.exception.AccessDeniedException;
import econo.buddybridge.auth.jwt.service.JwtTokenProvider;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.Role;
import econo.buddybridge.member.service.MemberService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class MemberTokenIdResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberTokenId.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Optional<String> token = jwtTokenProvider.extractTokenOptional(webRequest.getHeader(HttpHeaders.AUTHORIZATION));
        MemberTokenId memberTokenId = parameter.getParameterAnnotation(MemberTokenId.class);

        if (!memberTokenId.required()) {
            return token.map(jwtTokenProvider::getMemberIdFromAccessToken).orElse(null);
        }

        // required == true 이므로 memberId를 추출해 MemberRole을 확인
        Long memberId = token.map(jwtTokenProvider::getMemberIdFromAccessToken).orElseThrow(() -> AccessDeniedException.EXCEPTION);
        Member member = memberService.findMemberByIdOrThrow(memberId);

        checkMemberRole(member, memberTokenId.allowedRoles());
        return memberId;
    }

    private void checkMemberRole(Member member, Role[] roles) {
        if (!List.of(roles).contains(member.getRole())) {
            throw AccessDeniedException.EXCEPTION;
        }
    }
}
