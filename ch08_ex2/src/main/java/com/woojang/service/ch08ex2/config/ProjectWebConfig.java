package com.woojang.service.ch08ex2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/a").authenticated()
                .mvcMatchers(HttpMethod.POST, "/a").permitAll()
                .mvcMatchers(HttpMethod.GET, "/a/b/**").authenticated()
                .mvcMatchers(HttpMethod.GET, "/product/{code:^[0-9]*$}").permitAll()
                .mvcMatchers(HttpMethod.GET, "/code/{name}").authenticated()
                .anyRequest().denyAll();
        http.csrf().disable();
    }
}
