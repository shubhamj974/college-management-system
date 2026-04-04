package com.college.cms.modules.auth.controller;

import com.college.cms.common.base.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.college.cms.modules.auth.dto.*;
import com.college.cms.modules.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterRequest>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterRequest registerResponse = authService.register(request);

        ApiResponse<RegisterRequest> response = ApiResponse.<RegisterRequest>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);

        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(authResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}