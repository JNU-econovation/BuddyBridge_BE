package econo.buddybridge.auth.jwt;

import econo.buddybridge.auth.jwt.exception.InvalidAccessTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private static final String BEARER_PREFIX = "Bearer ";

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;

    public JwtTokenProvider(
        @Value("${custom.jwt.access-secret-key}") String accessSecretKey,
        @Value("${custom.jwt.refresh-secret-key}") String refreshSecretKey,
        @Value("${custom.jwt.access-token-expire-time}") Long accessTokenExpireTime,
        @Value("${custom.jwt.refresh-token-expire-time}") Long refreshTokenExpireTime
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes());
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public String generateAccessToken(Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .claim("id", memberId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + Duration.ofSeconds(accessTokenExpireTime).toMillis()))
                .signWith(accessSecretKey)  // JWS(JSON Web Signature)를 생성하기 위한 key 설정
                .compact();
    }

    public String generateRefreshToken(Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .claim("id", memberId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + Duration.ofSeconds(refreshTokenExpireTime).toMillis()))
                .signWith(refreshSecretKey)  // JWS(JSON Web Signature)를 생성하기 위한 key 설정
                .compact();
    }

    public String extractToken(String header) {
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return header.substring(BEARER_PREFIX.length());
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            throw InvalidAccessTokenException.EXCEPTION;
        }
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(accessSecretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }
}
