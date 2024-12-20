package geunbon.geunboni.domain.email.service;

import geunbon.geunboni.domain.email.entity.Email;
import geunbon.geunboni.domain.email.repository.EmailRepository;
import geunbon.geunboni.global.error.CustomException;
import geunbon.geunboni.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public void sendVerificationCode(String email) {
        if (emailRepository.findByEmailAndIsVerifiedTrue(email).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFIED_EMAIL);
        }

        String code = generateVerificationCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);

        Email emailVerification = emailRepository.findByEmail(email)
                .map(existing -> {
                    existing.setVerificationCode(code);
                    existing.setExpirationDate(expirationDate);
                    return existing;
                })
                .orElse(Email.builder()
                        .email(email)
                        .verificationCode(code)
                        .expirationDate(expirationDate)
                        .isVerified(false)
                        .build());

        emailRepository.save(emailVerification);
        emailService.sendEmail(email, code);
    }

    public String generateVerificationCode() {
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }

    @Transactional
    public void verifyCode(String email, String code) {
        Email verification = emailRepository.findByEmailAndVerificationCode(email, code).orElse(null);

        if (verification == null) {
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        } else if (verification.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_EMAIL);
        } else {
            verification.setVerified(true);
            verification.setVerificationCode(null);
            verification.setExpirationDate(null);
            emailRepository.save(verification);
        }
    }
}
