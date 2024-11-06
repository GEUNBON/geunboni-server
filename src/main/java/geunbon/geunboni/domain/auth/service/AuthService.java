package geunbon.geunboni.domain.auth.service;

import geunbon.geunboni.domain.auth.dto.request.ChangePasswordRequest;
import geunbon.geunboni.domain.auth.dto.request.LoginRequest;
import geunbon.geunboni.domain.auth.dto.request.ReissueRequest;
import geunbon.geunboni.domain.auth.dto.request.SignUpRequest;
import geunbon.geunboni.global.security.details.CustomUserDetails;
import geunbon.geunboni.global.security.jwt.dto.Jwt;

public interface AuthService {

    void signup(SignUpRequest request);

    Jwt login(LoginRequest request);

    Jwt reissue(ReissueRequest request);

    void changePassword(ChangePasswordRequest request, CustomUserDetails CustomUserDetails);

    void deleteAccount(CustomUserDetails CustomUserDetails);
}
