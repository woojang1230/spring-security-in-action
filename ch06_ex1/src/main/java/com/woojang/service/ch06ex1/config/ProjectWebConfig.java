package com.woojang.service.ch06ex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.woojang.service.ch06ex1.service.AuthenticationProviderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ProjectWebConfig {
    private final AuthenticationProviderService authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.formLogin(config -> config.defaultSuccessUrl("/main", true))
                .authorizeHttpRequests(req -> req.anyRequest().authenticated());
        return http.build();
    }

    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider);
    }
}
