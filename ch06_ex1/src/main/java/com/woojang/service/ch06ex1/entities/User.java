package com.woojang.service.ch06ex1.entities;

import java.util.List;

import jakarta.persistence.*;

import com.woojang.service.ch06ex1.entities.enums.EncryptionAlgorithm;
import lombok.Getter;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authority> authorities;
}
