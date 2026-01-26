package com.example.Bai2.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @GetMapping("/")
    public String Home() {
        return "Hello, Spring Boot!";
    }
}
