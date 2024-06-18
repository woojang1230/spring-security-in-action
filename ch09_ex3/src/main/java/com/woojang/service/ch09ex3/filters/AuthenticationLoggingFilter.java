package com.woojang.service.ch09ex3.filters;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final String requestId = httpServletRequest.getHeader("Request-Id");
        log.info("Successfuly authenticated request with id {}", requestId);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
