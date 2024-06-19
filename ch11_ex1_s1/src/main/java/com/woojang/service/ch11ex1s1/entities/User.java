package com.woojang.service.ch11ex1s1.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private String username;
    private String password;
}
