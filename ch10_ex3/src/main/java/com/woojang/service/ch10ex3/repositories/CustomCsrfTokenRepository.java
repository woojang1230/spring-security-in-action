package com.woojang.service.ch10ex3.repositories;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woojang.service.ch10ex3.entities.Token;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomCsrfTokenRepository implements CsrfTokenRepository {
    private final JpaTokenRepository jpaTokenRepository;

    @Override
    public CsrfToken generateToken(final HttpServletRequest request) {
        final String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Transactional
    @Override
    public void saveToken(
            final CsrfToken csrfToken,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final String identifier = request.getHeader("X-IDENTIFIER");
        this.jpaTokenRepository.findTokenByIdentifier(identifier)
                .ifPresentOrElse(token -> {
                    token.setToken(csrfToken.getToken());
                }, () -> {
                    final Token token = new Token();
                    token.setToken(csrfToken.getToken());
                    token.setIdentifier(identifier);
                    this.jpaTokenRepository.save(token);
                });
    }

    @Override
    public CsrfToken loadToken(final HttpServletRequest request) {
        final String identifier = request.getHeader("X-IDENTIFIER");
        return this.jpaTokenRepository.findTokenByIdentifier(identifier)
                .map(token -> new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken()))
                .orElse(null);
    }
}
