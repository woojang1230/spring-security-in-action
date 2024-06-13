package com.woojang.service.ch06ex1.entities;

import lombok.Getter;

import javax.persistence.*;

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
