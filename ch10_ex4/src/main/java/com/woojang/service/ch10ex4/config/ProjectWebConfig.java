package com.woojang.service.ch10ex4.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors(cors -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("example.com", "example.org")
                );
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE")
                );
                return config;
            };
            cors.configurationSource(source);
        });
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
    }
}
