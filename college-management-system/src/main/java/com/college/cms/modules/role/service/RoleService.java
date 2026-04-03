package com.college.cms.modules.role.service;

import com.college.cms.common.base.enums.RoleType;
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
        RoleType roleType;
        try {
            roleType = RoleType.valueOf(param.getName().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CustomException(
                    "Invalid role type",
                    HttpStatus.BAD_REQUEST,
                    "ROLE_002"
            );
        }

        if (roleRepository.existsByName(roleType.getDisplayName())) {
            throw new CustomException(
                    "Role already exists",
                    HttpStatus.CONFLICT,
                    "ROLE_001"
            );
        }

        Role role = Role.builder()
                .name(roleType.getDisplayName())
                .code(roleType.name())
                .build();

        return roleRepository.save(role);
    }
}