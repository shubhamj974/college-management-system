package com.college.cms.modules.role.service;

import com.college.cms.common.base.constant.ErrorCodes;
import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.dto.RoleRequest;
import com.college.cms.modules.role.entity.Role;
import com.college.cms.modules.role.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createRole(RoleRequest param) {

        if (param == null) {
            throw new CustomException(
                    "Request cannot be null",
                    HttpStatus.BAD_REQUEST,
                    ErrorCodes.VALIDATION_ERROR

            );
        }

        if (param.getName() == null || param.getName().trim().isEmpty()) {
            throw new CustomException(
                    "Role name is required",
                    HttpStatus.BAD_REQUEST,
                    ErrorCodes.VALIDATION_ERROR
            );
        }

        if (param.getCode() == null || param.getCode().trim().isEmpty()) {
            throw new CustomException(
                    "Role code is required",
                    HttpStatus.BAD_REQUEST,
                    ErrorCodes.VALIDATION_ERROR
            );
        }

        if (roleRepository.existsByName(param.getCode().trim().toUpperCase())) {
            throw new CustomException(
                    "Role with this code already exists",
                    HttpStatus.CONFLICT,
                    "ROLE_001"
            );
        }

        Role role = Role.builder()
                .name(param.getName().trim())
                .code(param.getCode().trim().toUpperCase())
                .build();

        return roleRepository.save(role);
    }
}