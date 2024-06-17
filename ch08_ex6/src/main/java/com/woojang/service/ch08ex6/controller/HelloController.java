package com.woojang.service.ch08ex6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/video/{country}/{language}")
    public String video(@PathVariable final String country, @PathVariable final String language) {
        return "Video allowed for " + country + " " + language;
    }
}
