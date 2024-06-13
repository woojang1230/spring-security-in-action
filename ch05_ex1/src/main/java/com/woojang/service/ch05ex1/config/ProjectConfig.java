package com.woojang.service.ch05ex1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class ProjectConfig {
    private final AuthenticationProvider authenticationProvider;

    public ProjectConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider);
    }
}
