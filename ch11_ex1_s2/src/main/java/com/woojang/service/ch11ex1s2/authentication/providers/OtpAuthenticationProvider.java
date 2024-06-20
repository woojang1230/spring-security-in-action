package com.woojang.service.ch11ex1s2.authentication.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.woojang.service.ch11ex1s2.authentication.OtpAuthentication;
import com.woojang.service.ch11ex1s2.authentication.proxy.AuthenticationServerProxy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OtpAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationServerProxy proxy;
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String code = String.valueOf(authentication.getCredentials());
        if (proxy.sendOtp(username, code)) {
            return new OtpAuthentication(username, code);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return OtpAuthentication.class.isAssignableFrom(authentication);
    }
}
