package com.woojang.service.ch10ex2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProjectController {
    @PostMapping("/add")
    public String add(@RequestParam(name = "name") final String name) {
        log.info("Adding product {}", name);
        return "main.html";
    }
}
