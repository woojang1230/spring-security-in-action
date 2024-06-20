package com.woojang.service.ch11ex1s2.authentication.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.woojang.service.ch11ex1s2.authentication.UsernamePasswordAuthentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String signingKey;

    public JwtAuthenticationFilter(@Value("${jwt.signing.key}") final String signingKey) {
        this.signingKey = signingKey;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain
    ) throws ServletException, IOException {
        final String jwt = request.getHeader("Authorization");
        final UsernamePasswordAuthentication auth = makeUsernamePasswordAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthentication makeUsernamePasswordAuthentication(final String jwt) {
        final String username = extractUsername(jwt);
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));
        return new UsernamePasswordAuthentication(username, null, authorities);
    }

    private String extractUsername(final String jwt) {
        final SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        final String username = String.valueOf(claims.get("username"));
        return username;
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login") && request.getMethod().equals("POST");
    }
}
