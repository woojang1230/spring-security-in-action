package com.woojang.service.ch11ex1s1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woojang.service.ch11ex1s1.entities.Otp;

public interface OtpRepository extends JpaRepository<Otp, String> {
    Optional<Otp> findOtpByUsername(final String username);
}
