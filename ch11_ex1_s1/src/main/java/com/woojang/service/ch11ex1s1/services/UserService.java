package com.woojang.service.ch11ex1s1.services;

import java.util.Objects;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woojang.service.ch11ex1s1.entities.Otp;
import com.woojang.service.ch11ex1s1.entities.User;
import com.woojang.service.ch11ex1s1.repositories.OtpRepository;
import com.woojang.service.ch11ex1s1.repositories.UserRepository;
import com.woojang.service.ch11ex1s1.utils.GenerateCodeUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    @Transactional
    public void addUser(final User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    @Transactional
    public void auth(final User user) {
        this.userRepository.findUserByUsername(user.getUsername())
                .ifPresentOrElse(u -> {
                    if (this.passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                        renewOtp(u);
                        return;
                    }
                    throw new BadCredentialsException("Bad credentials.");
                }, () -> {
                    throw new BadCredentialsException("Bad credentials.");
                });
    }

    private void renewOtp(final User user) {
        String code = GenerateCodeUtils.generateCode();
        this.otpRepository.findOtpByUsername(user.getUsername())
                .ifPresentOrElse(otp -> {
                    otp.setCode(code);
                }, () -> {
                    final Otp otp = new Otp(user.getUsername(), code);
                    this.otpRepository.save(otp);
                });
    }

    @Transactional(readOnly = true)
    public boolean check(final Otp otpToValidate) {
        return this.otpRepository.findOtpByUsername(otpToValidate.getUsername())
                .map(otp -> Objects.equals(otpToValidate.getCode(), otp.getCode()))
                .orElse(false);
    }
}
