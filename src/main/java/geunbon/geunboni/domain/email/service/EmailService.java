package geunbon.geunboni.domain.email.service;

public interface EmailService {

    void sendEmail(String email, String code);

    void deleteEmail(String email);
}