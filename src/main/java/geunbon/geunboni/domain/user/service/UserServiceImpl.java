package geunbon.geunboni.domain.user.service;

import geunbon.geunboni.domain.user.dto.response.UserResponse;
import geunbon.geunboni.domain.user.entity.User;
import geunbon.geunboni.domain.user.repository.UserRepository;
import geunbon.geunboni.global.error.CustomException;
import geunbon.geunboni.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserResponse getMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponse(
                user.getId(),
                user.getSurName(),
                user.getGivenName(),
                user.getEmail()
        );
    }
}
