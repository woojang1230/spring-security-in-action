package com.woojang.service.ch11ex1s2.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class OtpAuthentication extends UsernamePasswordAuthenticationToken {
    public OtpAuthentication(final Object principal, final Object credentials) {
        super(principal, credentials);
    }
}
