package com.woojang.service.ch06ex1.repositories;

import com.woojang.service.ch06ex1.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
