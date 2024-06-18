package com.woojang.service.ch09ex2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.woojang.service.ch09ex2.filters.StaticKeyAuthenticationFilter;

@Configuration
public class ProjectWebConfig {
    private final String authorizationKey;

    public ProjectWebConfig(@Value("${authorization.key}") final String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.addFilterAt(
                        new StaticKeyAuthenticationFilter(authorizationKey),
                        BasicAuthenticationFilter.class
                )
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
