package com.woojang.service.ch11ex1s1.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Otp {
    @Id
    private String username;
    private String code;
}
