package geunbon.geunboni.global.security.jwt.dto;

public record Jwt(
        String accessToken,
        String refreshToken
) {
}