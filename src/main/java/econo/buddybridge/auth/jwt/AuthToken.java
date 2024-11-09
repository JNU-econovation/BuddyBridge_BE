package econo.buddybridge.auth.jwt;

public record AuthToken(
        String accessToken,
        String refreshToken,
        String grantType,
        Long expiresIn
) {

    public static AuthToken of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthToken(accessToken, refreshToken, grantType, expiresIn);
    }
}
