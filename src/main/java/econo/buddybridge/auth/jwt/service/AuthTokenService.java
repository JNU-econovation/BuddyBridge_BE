package econo.buddybridge.auth.jwt.service;

import econo.buddybridge.auth.jwt.AuthToken;
import econo.buddybridge.auth.jwt.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthTokenService {

    private static final String GRANT_TYPE = "Bearer";

    @Value("${custom.jwt.access-token-expire-time}")
    private Long accessTokenExpireTime;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthToken generateAuthToken(Long memberId) {
        String accessToken = jwtTokenProvider.generateAccessToken(memberId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(memberId);

        return AuthToken.of(accessToken, refreshToken, GRANT_TYPE, accessTokenExpireTime);
    }

    @Transactional
    public AuthToken reissue(String refreshToken) {
        Long memberId = jwtTokenProvider.getMemberIdFromRefreshToken(refreshToken);

        if (!jwtTokenProvider.existsByMemberIdAndRefreshToken(refreshToken)) {
            throw InvalidRefreshTokenException.EXCEPTION;
        }

        return generateAuthToken(memberId);
    }

    @Transactional
    public void logout(Long memberId) {
        jwtTokenProvider.deleteByMemberId(memberId);
    }
}
