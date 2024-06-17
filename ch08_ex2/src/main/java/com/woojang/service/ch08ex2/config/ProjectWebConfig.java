package com.woojang.service.ch08ex2.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        http.httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(makeMvcMatcher(introspector, "/a", HttpMethod.GET)).authenticated()
                        .requestMatchers(makeMvcMatcher(introspector, "/a", HttpMethod.POST)).permitAll()
                        .requestMatchers(makeMvcMatcher(introspector, "/a/b/**", HttpMethod.GET)).authenticated()
                        .requestMatchers(makeMvcMatcher(introspector, "/product/{code:^[0-9]*$}", HttpMethod.GET)).authenticated()
                        .requestMatchers(makeMvcMatcher(introspector, "/code/{name}", HttpMethod.GET)).permitAll()
                        .anyRequest().denyAll());
        return http.build();
    }

    private RequestMatcher makeMvcMatcher(
            final HandlerMappingIntrospector introspector, final String pattern, final HttpMethod httpMethod
    ) {
        final MvcRequestMatcher matcher = new MvcRequestMatcher(introspector, pattern);
        matcher.setMethod(httpMethod);
        return matcher;
    }
}
