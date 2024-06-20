package com.woojang.service.ch11ex1s2.authentication.modle;

import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private String code;
}
