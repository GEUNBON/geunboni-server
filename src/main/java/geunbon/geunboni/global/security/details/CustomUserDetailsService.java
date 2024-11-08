package geunbon.geunboni.global.security.details;

import geunbon.geunboni.domain.user.entity.User;
import geunbon.geunboni.domain.user.repository.UserRepository;
import geunbon.geunboni.global.error.CustomException;
import geunbon.geunboni.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<GrantedAuthority> authorities;

        if (user.getRole() == null) {
            authorities = Collections.emptyList();
        } else {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
