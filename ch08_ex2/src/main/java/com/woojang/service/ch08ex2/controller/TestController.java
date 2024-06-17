package com.woojang.service.ch08ex2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping("/a")
    public String postEndpointA() {
        return "Works!";
    }

    @GetMapping("/a")
    public String getEndpointA() {
        return "Works!";
    }

    @GetMapping("/a/b")
    public String getEndpointB() {
        return "Works!";
    }

    @GetMapping("/a/b/c")
    public String getEndpointC() {
        return "Works!";
    }

    @GetMapping("/a/b/c/d")
    public String getEndpointD() {
        return "Works!";
    }

    @GetMapping("/product/{code}")
    public String productCode(@PathVariable final String code) {
        return code;
    }

    @GetMapping("/code/{name}")
    public String codeName(@PathVariable final String name) {
        return name;
    }
}
