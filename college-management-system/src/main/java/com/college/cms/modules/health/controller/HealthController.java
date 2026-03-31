package com.college.cms.modules.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HealthController {
    @GetMapping
    public String test() {
        return "Collage management system is running 🚀";
    }
}
