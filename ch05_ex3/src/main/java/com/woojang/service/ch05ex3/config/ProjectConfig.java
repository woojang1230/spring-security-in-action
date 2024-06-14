package com.woojang.service.ch05ex3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic(configurer -> {
            configurer.realmName("OTHER");
            configurer.authenticationEntryPoint(new CustomEntryPoint());
        });
        http.authorizeRequests().anyRequest().authenticated();
    }
}
