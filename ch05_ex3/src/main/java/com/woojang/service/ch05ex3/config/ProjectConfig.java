package com.woojang.service.ch05ex3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .httpBasic(configurer -> {
                    configurer.realmName("OTHER");
                    configurer.authenticationEntryPoint(new CustomEntryPoint());
                });
        return http.build();
    }
}
