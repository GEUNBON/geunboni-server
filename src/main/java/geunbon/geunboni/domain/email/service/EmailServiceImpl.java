package geunbon.geunboni.domain.email.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import geunbon.geunboni.domain.email.repository.EmailRepository;
import geunbon.geunboni.global.config.email.EmailConfig;
import geunbon.geunboni.global.error.CustomException;
import geunbon.geunboni.global.error.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;
    private final EmailRepository emailRepository;

    public void sendEmail(String email, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            message.setFrom(emailConfig.getUsername());
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("모두랑 이메일 인증코드 : " + code);

            ClassPathResource resource = new ClassPathResource("/static/verify.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String content = FileCopyUtils.copyToString(reader);
            content = content.replace("{code}", code);

            helper.setText(content, true);

            helper.addInline("image", new ClassPathResource("static/모두랑 인증코드 이미지.png"));

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            logger.error("{}에게 인증 이메일 전송 실패: {}", email, e.getMessage());
        }
    }

    @Transactional
    public void deleteEmail(String email) {
        if (emailRepository.findByEmail(email).isEmpty()) {
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        emailRepository.deleteByEmail(email);
    }
}
