package geunbon.geunboni.domain.auth.controller;

import geunbon.geunboni.domain.auth.dto.request.ChangePasswordRequest;
import geunbon.geunboni.domain.auth.dto.request.LoginRequest;
import geunbon.geunboni.domain.auth.dto.request.ReissueRequest;
import geunbon.geunboni.domain.auth.dto.request.SignUpRequest;
import geunbon.geunboni.domain.auth.service.AuthService;
import geunbon.geunboni.global.security.jwt.dto.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "Auth")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequest request) {
        authService.signup(request);
        return ResponseEntity.status(201).body(null);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Jwt> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "엑세스 토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<Jwt> reissue(@Valid @RequestBody ReissueRequest request) {
        return ResponseEntity.ok(authService.reissue(request));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/resign")
    public ResponseEntity<Void> deleteAccount() {
        authService.deleteAccount();
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity.ok(null);
    }
}
