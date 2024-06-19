package com.woojang.service.ch11ex1s1.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.woojang.service.ch11ex1s1.entities.Otp;
import com.woojang.service.ch11ex1s1.entities.User;
import com.woojang.service.ch11ex1s1.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/user/add")
    public void addUser(@RequestBody final User user) {
        this.userService.addUser(user);
    }

    @PostMapping("/user/auth")
    public void auth(@RequestBody final User user) {
        this.userService.auth(user);
    }

    @PostMapping("/otp/check")
    public void check(@RequestBody final Otp otp, final HttpServletResponse response) {
        if (this.userService.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
