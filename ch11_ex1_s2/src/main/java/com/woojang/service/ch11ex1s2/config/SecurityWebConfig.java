package com.woojang.service.ch11ex1s2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.woojang.service.ch11ex1s2.authentication.filters.InitialAuthenticationFilter;
import com.woojang.service.ch11ex1s2.authentication.filters.JwtAuthenticationFilter;
import com.woojang.service.ch11ex1s2.authentication.providers.OtpAuthenticationProvider;
import com.woojang.service.ch11ex1s2.authentication.providers.UsernamePasswordAuthenticationProvider;

@Configuration
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private InitialAuthenticationFilter initialAuthenticationFilter;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.otpAuthenticationProvider)
                .authenticationProvider(this.usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterAt(this.initialAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(this.jwtAuthenticationFilter, BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
