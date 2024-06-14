package com.woojang.service.ch05ex4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public ProjectConfig(final AuthenticationSuccessHandler authenticationSuccessHandler,
                         final AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                .successHandler(this.authenticationSuccessHandler)
                .failureHandler(this.authenticationFailureHandler)
                .and()
                .httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }


    /**
     * 권한이 부여된 임의의 사용자 생성
     */
    @Bean
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        final UserDetails user = User.withUsername("Jang")
                .password("12345")
                .authorities("read")
                .build();
        userDetailsManager.createUser(user);
        return userDetailsManager;
    }

    /**
     * UserDetailsService 빈 등록으로 PasswordEncoder 빈도 함께 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
