package com.college.cms.modules.auth.service;

import com.college.cms.common.base.constant.ErrorCodes;
import com.college.cms.common.base.dto.ApiResponse;
import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.entity.Role;
import com.college.cms.modules.role.repository.RoleRepository;
import com.college.cms.modules.user.entity.User;
import com.college.cms.modules.user.entity.UserRole;
import com.college.cms.modules.user.repository.UserRepository;
import com.college.cms.modules.user.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.college.cms.modules.auth.dto.*;
import com.college.cms.security.JwtService;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public RegisterRequest register(RegisterRequest request) {
        List<Role> roles = roleRepository.findAllById(request.getRoleIds());
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(
                    "Email already registered",
                    HttpStatus.BAD_REQUEST,
                    ErrorCodes.USER_ALREADY_EXISTS
            );
        }
        if (roles.isEmpty() || roles.size() != request.getRoleIds().size()) {
            throw new CustomException(
                    "Role not found",
                    HttpStatus.NOT_FOUND,
                    ErrorCodes.ROLE_NOT_FOUND
            );
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user); // IMPORTANT

        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }

        return RegisterRequest.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(user.getEmail())
                .roleIds(request.getRoleIds())
                .build();
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