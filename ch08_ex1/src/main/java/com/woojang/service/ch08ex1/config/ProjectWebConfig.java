package com.woojang.service.ch08ex1.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new MvcRequestMatcher(introspector, "/hello")).hasRole("ADMIN")
                        .requestMatchers(new MvcRequestMatcher(introspector, "/ciao")).hasRole("MANAGER")
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
