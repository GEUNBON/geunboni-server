package geunbon.geunboni.domain.auth.controller;

import geunbon.geunboni.domain.auth.dto.request.ChangePasswordRequest;
import geunbon.geunboni.domain.auth.dto.request.LoginRequest;
import geunbon.geunboni.domain.auth.dto.request.ReissueRequest;
import geunbon.geunboni.domain.auth.dto.request.SignUpRequest;
import geunbon.geunboni.domain.auth.service.AuthService;
import geunbon.geunboni.global.common.BaseResponse;
import geunbon.geunboni.global.security.details.CustomUserDetails;
import geunbon.geunboni.global.security.jwt.dto.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "Auth")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> signup(@Valid @RequestBody SignUpRequest request) {
        authService.signup(request);
        return BaseResponse.of(null, 201);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Jwt>> login(@Valid @RequestBody LoginRequest request) {
        return BaseResponse.of(authService.login(request));
    }

    @Operation(summary = "엑세스 토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<Jwt>> reissue(@Valid @RequestBody ReissueRequest request) {
        return BaseResponse.of(authService.reissue(request));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/resign")
    public ResponseEntity<BaseResponse<Void>> deleteAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        authService.deleteAccount(customUserDetails);
        return BaseResponse.of(null);
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/password")
    public ResponseEntity<BaseResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        authService.changePassword(request, customUserDetails);
        return BaseResponse.of(null);
    }
}
