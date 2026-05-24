package com.testmate.ai.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/framework")
public class FrameworkController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "application", "TestMate AI",
                "timestamp", LocalDateTime.now()
        );
    }

    @GetMapping("/metadata")
    public Map<String, Object> metadata() {
        return Map.of(
                "name", "TestMate AI",
                "phase", "Phase 2",
                "backend", "Spring Boot",
                "frameworkModules", List.of("AI", "API", "WEB", "MOBILE"),
                "supportedProfiles", List.of("ai", "api", "web", "mobile", "all"),
                "executionModes", List.of("mock", "local", "sauce")
        );
    }
}
