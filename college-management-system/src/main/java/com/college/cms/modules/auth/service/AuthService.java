package com.college.cms.modules.auth.service;

import com.college.cms.common.base.constant.ErrorCodes;
import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.entity.Role;
import com.college.cms.modules.role.entity.RolePermission;
import com.college.cms.modules.role.repository.RolePermissionRepository;
import com.college.cms.modules.role.repository.RoleRepository;
import com.college.cms.modules.user.entity.User;
import com.college.cms.modules.user.entity.UserRole;
import com.college.cms.modules.user.repository.UserRepository;
import com.college.cms.modules.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.college.cms.modules.auth.dto.*;
import com.college.cms.security.JwtService;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
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
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .employeeId(request.getEmployeeId())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .isActive(true)
                .isEmailVerified(false)
                .build();
        user = userRepository.save(user); // IMPORTANT

        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }

        return RegisterRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .employeeId(user.getEmployeeId())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .city(user.getCity())
                .state(user.getState())
                .pincode(user.getPincode())
                .roleIds(request.getRoleIds())
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(
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

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new CustomException(
                    "Account is deactivated. Please contact admin",
                    HttpStatus.FORBIDDEN,
                    ErrorCodes.ACCESS_DENIED
            );
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        // Build user info
        AuthResponse.UserInfo userInfo = AuthResponse.UserInfo.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .build();

        // Build roles with permissions
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        List<AuthResponse.RoleInfo> roleInfoList = new ArrayList<>();

        for (UserRole userRole : userRoles) {
            Role role = userRole.getRole();
            List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(role.getId());

            // Group permissions by module
            Map<Long, AuthResponse.ModulePermission> modulePermissionMap = new LinkedHashMap<>();

            for (RolePermission rp : rolePermissions) {
                Long moduleId = rp.getModule().getId();

                if (!modulePermissionMap.containsKey(moduleId)) {
                    modulePermissionMap.put(moduleId, AuthResponse.ModulePermission.builder()
                            .moduleId(moduleId)
                            .moduleName(rp.getModule().getName())
                            .moduleCode(rp.getModule().getCode())
                            .icon(rp.getModule().getIcon())
                            .routePath(rp.getModule().getRoutePath())
                            .permissions(new ArrayList<>())
                            .build());
                }

                modulePermissionMap.get(moduleId).getPermissions().add(rp.getPermission().getCode());
            }

            AuthResponse.RoleInfo roleInfo = AuthResponse.RoleInfo.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .code(role.getCode())
                    .permissions(new ArrayList<>(modulePermissionMap.values()))
                    .build();

            roleInfoList.add(roleInfo);
        }

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(86400)
                .user(userInfo)
                .roles(roleInfoList)
                .build();
    }
}