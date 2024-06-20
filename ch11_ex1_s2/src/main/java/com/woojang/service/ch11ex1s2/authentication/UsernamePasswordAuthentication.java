package com.woojang.service.ch11ex1s2.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {
    public UsernamePasswordAuthentication(final Object principal, final Object credentials) {
        super(principal, credentials);
    }

    public UsernamePasswordAuthentication(
            final Object principal,
            final Object credentials,
            final Collection<? extends GrantedAuthority> authorities
    ) {
        super(principal, credentials, authorities);
    }
}
