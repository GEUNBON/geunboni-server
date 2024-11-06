package geunbon.geunboni.domain.auth.repository;

public interface RefreshTokenRepository {

    void save(String email, String refreshToken);

    String findByEmail(String email);

    Boolean existsByEmail(String email);
}
