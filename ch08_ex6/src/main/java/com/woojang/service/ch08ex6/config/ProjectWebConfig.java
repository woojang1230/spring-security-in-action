package com.woojang.service.ch08ex6.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new RegexRequestMatcher(".*/(us|uk|ca)+/(en|fr).*", HttpMethod.GET.name())).authenticated()
                        .anyRequest().hasAuthority("premium")
                );
        return http.build();
    }
}
