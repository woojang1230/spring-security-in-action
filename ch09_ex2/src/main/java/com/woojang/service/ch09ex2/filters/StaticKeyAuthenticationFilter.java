package com.woojang.service.ch09ex2.filters;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

public class StaticKeyAuthenticationFilter implements Filter {
    private final String authorizationKey;

    public StaticKeyAuthenticationFilter(@Value("${authorization.key}") final String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        final String authorization = httpRequest.getHeader("Authorization");
        if (Objects.equals(this.authorizationKey, authorization)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
