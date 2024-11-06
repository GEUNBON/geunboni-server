package geunbon.geunboni.domain.auth.service;

import geunbon.geunboni.domain.auth.dto.request.ChangePasswordRequest;
import geunbon.geunboni.domain.auth.dto.request.LoginRequest;
import geunbon.geunboni.domain.auth.dto.request.ReissueRequest;
import geunbon.geunboni.domain.auth.dto.request.SignUpRequest;
import geunbon.geunboni.domain.auth.repository.RefreshTokenRepository;
import geunbon.geunboni.domain.email.entity.Email;
import geunbon.geunboni.domain.email.repository.EmailRepository;
import geunbon.geunboni.domain.user.entity.User;
import geunbon.geunboni.domain.user.repository.UserRepository;
import geunbon.geunboni.global.error.CustomException;
import geunbon.geunboni.global.error.ErrorCode;
import geunbon.geunboni.global.security.details.CustomUserDetails;
import geunbon.geunboni.global.security.jwt.dto.Jwt;
import geunbon.geunboni.global.security.jwt.enums.JwtType;
import geunbon.geunboni.global.security.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Override
    public void signup(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.HAS_EMAIL);
        }

        Email verification = emailRepository.findByEmail(request.getEmail()).orElse(null);
        if (verification == null || !verification.isVerified()) {
            throw new CustomException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        User user = User.builder()
                .surName(request.getSurName())
                .givenName(request.getGivenName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Jwt login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        return jwtProvider.generateToken(request.getEmail());
    }

    @Transactional(readOnly = true)
    @Override
    public Jwt reissue(ReissueRequest request) {
        String email = jwtProvider.getSubject(request.getRefreshToken());

        if (userRepository.findByEmail(email).isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (!refreshTokenRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (!refreshTokenRepository.findByEmail(email).equals(request.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (jwtProvider.getType(request.getRefreshToken()) != JwtType.REFRESH)
            throw new CustomException(ErrorCode.INVALID_TOKEN_TYPE);

        return jwtProvider.generateToken(email);
    }

    @Transactional
    @Override
    public void deleteAccount(CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();

        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequest request, CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
