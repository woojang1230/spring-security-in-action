package com.woojang.service.ch10ex2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        final UserDetails user1 = User.withUsername("j")
                .password("12345")
                .authorities("READ")
                .build();
        manager.createUser(user1);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
