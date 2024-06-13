package com.woojang.service.ch06ex1.service;

import com.woojang.service.ch06ex1.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.woojang.service.ch06ex1.entities.enums.EncryptionAlgorithm.BCRYPT;
import static com.woojang.service.ch06ex1.entities.enums.EncryptionAlgorithm.SCRYPT;

@RequiredArgsConstructor
@Service
public class AuthenticationProviderService implements AuthenticationProvider {
    private final JpaUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final CustomUserDetails user = this.userDetailsService.loadUserByUsername(username);
        if (Objects.equals(user.getUser().getAlgorithm(), BCRYPT)) {
            return checkPassword(user, password, bCryptPasswordEncoder);
        }
        if (Objects.equals(user.getUser().getAlgorithm(), SCRYPT)) {
            return checkPassword(user, password, sCryptPasswordEncoder);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    private Authentication checkPassword(final CustomUserDetails user, final String rawPassword,
                                         final PasswordEncoder encoder) {
        if (encoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
