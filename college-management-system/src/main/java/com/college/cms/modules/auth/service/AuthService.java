package com.college.cms.modules.auth.service;

import com.college.cms.common.base.constant.ErrorCodes;
import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.repository.RoleRepository;
import com.college.cms.modules.user.entity.User;
import com.college.cms.modules.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.college.cms.modules.auth.dto.*;
import com.college.cms.security.JwtService;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public String register(RegisterRequest request) {

//        Role role = roleRepository.findByName(request.getRole())
//                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {
        System.out.println(request);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new CustomException(
                                "Invalid email or password",
                                HttpStatus.UNAUTHORIZED,
                                ErrorCodes.AUTH_INVALID
                        ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(
                    "Invalid email or password",
                    HttpStatus.UNAUTHORIZED,
                    ErrorCodes.AUTH_INVALID
            );
        }

        String token = jwtService.generateToken(user.getEmail());
        System.out.println("Token: " + token);
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(86400)
                .email(user.getEmail())
                .build();
    }
}