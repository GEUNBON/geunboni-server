package geunbon.geunboni.domain.email.repository;

import geunbon.geunboni.domain.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByEmail(String email);

    Optional<Email> findByEmailAndVerificationCode(String email, String code);

    Optional<Email> findByEmailAndIsVerifiedTrue(String email);

    void deleteByEmail(String email);
}
