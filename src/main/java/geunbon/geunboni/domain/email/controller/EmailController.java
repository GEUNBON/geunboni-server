package geunbon.geunboni.domain.email.controller;

import geunbon.geunboni.domain.email.dto.request.EmailSendRequest;
import geunbon.geunboni.domain.email.dto.request.EmailVerifyRequest;
import geunbon.geunboni.domain.email.service.EmailService;
import geunbon.geunboni.domain.email.service.EmailVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "이메일", description = "Email")
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;

    @Operation(summary = "이메일 전송")
    @PostMapping("/send")
    public ResponseEntity<Void> sendVerificationCode(@Valid @RequestBody EmailSendRequest request) {
        emailVerificationService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok().body(null);
    }

    @Operation(summary = "이메일 인증")
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
        emailVerificationService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok().body(null);
    }
}
