package com.woojang.service.ch11ex1s1.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenerateCodeUtils {

    public static String generateCode() {
        try {
            final SecureRandom random = SecureRandom.getInstanceStrong();
            return String.valueOf(random.nextInt(9000) + 1000);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code.");
        }
    }
}
