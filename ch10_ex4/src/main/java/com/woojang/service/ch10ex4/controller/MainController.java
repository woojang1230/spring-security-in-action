package com.woojang.service.ch10ex4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
    @GetMapping("/")
    public String main() {
        return "main.html";
    }

    @PostMapping("/test")
//    @CrossOrigin("http://localhost:9104")
    public ResponseEntity<String> test() {
        log.info("Test method called");
        return ResponseEntity.ok("HELLO");
    }
}
