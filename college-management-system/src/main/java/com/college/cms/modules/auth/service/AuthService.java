package com.college.cms.modules.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.college.cms.modules.auth.dto.*;
import com.college.cms.modules.auth.entity.*;
import com.college.cms.modules.auth.repository.*;
import com.college.cms.security.JwtService;

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

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}