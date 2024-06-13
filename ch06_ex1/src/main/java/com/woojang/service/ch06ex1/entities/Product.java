package com.woojang.service.ch06ex1.entities;

import com.woojang.service.ch06ex1.entities.enums.Currency;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
