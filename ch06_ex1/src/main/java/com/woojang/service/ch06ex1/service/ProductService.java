package com.woojang.service.ch06ex1.service;

import com.woojang.service.ch06ex1.entities.Product;
import com.woojang.service.ch06ex1.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }
}
