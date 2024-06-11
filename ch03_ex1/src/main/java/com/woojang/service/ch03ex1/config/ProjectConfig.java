package com.woojang.service.ch03ex1.config;

import com.woojang.service.ch03ex1.model.User;
import com.woojang.service.ch03ex1.service.InMemoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails user = new User("Jang", "12345", "read");
        return new InMemoryUserDetailsService(List.of(user));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
