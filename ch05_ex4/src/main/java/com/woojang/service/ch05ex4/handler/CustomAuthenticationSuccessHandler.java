package com.woojang.service.ch05ex4.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication) throws IOException, ServletException {
        authentication.getAuthorities()
                .stream()
                .filter(auth -> Objects.equals(auth.getAuthority(), "read"))
                .findFirst()
                .ifPresentOrElse(auth -> redirectPage(response, "/home"),
                        () -> redirectPage(response, "/error"));
    }

    private void redirectPage(final HttpServletResponse response, final String path) {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
