package com.woojang.service.ch10ex1.filters;

import java.io.IOException;

import javax.servlet.*;

import org.springframework.security.web.csrf.CsrfToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsrfTokenLogger implements Filter {
    @Override
    public void doFilter(
            final ServletRequest request, final ServletResponse response, final FilterChain chain
    ) throws IOException, ServletException {
        final CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        log.info("CSRF token {}", token.getToken());
        chain.doFilter(request, response);
    }
}
