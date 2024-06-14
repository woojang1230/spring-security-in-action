package com.woojang.service.ch06ex1.controller;

import com.woojang.service.ch06ex1.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainPageController {
    private final ProductService productService;

    @GetMapping("/main")
    public String main(final Authentication authentication, final Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("products", productService.findAll());
        return "main.html";
    }
}
