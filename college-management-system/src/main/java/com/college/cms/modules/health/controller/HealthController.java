package com.college.cms.modules.health.controller;

import com.college.cms.common.base.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        Map<String, String> data = Map.of(
                "application", "College Management System",
                "status", "UP"
        );

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Service is running")
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }
}
