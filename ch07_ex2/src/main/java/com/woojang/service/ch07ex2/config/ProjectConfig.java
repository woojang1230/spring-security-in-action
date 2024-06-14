package com.woojang.service.ch07ex2.config;

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
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        final UserDetails user1 = User.withUsername("john")
                .password("12345")
                .authorities("read")
                .build();
        final UserDetails user2 = User.withUsername("jane")
                .password("12345")
                .authorities("read", "write", "delete")
                .build();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
