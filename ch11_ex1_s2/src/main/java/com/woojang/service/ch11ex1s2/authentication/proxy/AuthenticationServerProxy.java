package com.woojang.service.ch11ex1s2.authentication.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.woojang.service.ch11ex1s2.authentication.modle.User;

@Component
public class AuthenticationServerProxy {
    private final RestTemplate rest;
    private final String baseUrl;

    public AuthenticationServerProxy(final RestTemplate rest,
                                     @Value("${auth.server.base.url}") final String baseUrl) {
        this.rest = rest;
        this.baseUrl = baseUrl;
    }

    public void sendAuth(final String username, final String password) {
        final String url = this.baseUrl + "/user/auth";
        final User body = new User();
        body.setUsername(username);
        body.setPassword(password);

        final HttpEntity<User> request = new HttpEntity<>(body);
        rest.postForEntity(url, request, Void.class);
    }

    public boolean sendOtp(final String username, final String code) {
        final String url = this.baseUrl + "/otp/check";
        final User body = new User();
        body.setUsername(username);
        body.setCode(code);
        final HttpEntity<User> request = new HttpEntity<>(body);
        final ResponseEntity<Void> response = rest.postForEntity(url, request, Void.class);
        return response.getStatusCode().equals(HttpStatus.OK);
    }
}
