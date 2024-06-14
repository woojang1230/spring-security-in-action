package com.woojang.service.ch06ex1.entities;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
