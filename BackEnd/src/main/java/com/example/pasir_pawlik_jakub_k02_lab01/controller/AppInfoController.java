package com.example.pasir_pawlik_jakub_k02_lab01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")

public class AppInfoController {
    @GetMapping("/info")
    public Map<String, Object> getAppInfo() {
        return Map.of("appName", "Aplikacja Budżetowa",
                "version", "1.0","message",
                "Witaj w aplikacji budżetowej stworzonej ze Spring Boot!"
        );
    }
}
