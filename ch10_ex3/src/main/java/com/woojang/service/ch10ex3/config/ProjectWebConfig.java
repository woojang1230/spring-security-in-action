package com.woojang.service.ch10ex3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    private final CsrfTokenRepository csrfTokenRepository;
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.csrfTokenRepository(this.csrfTokenRepository)
                .ignoringAntMatchers("/ciao"));
        http.authorizeRequests()
                .anyRequest().permitAll();
    }
}
