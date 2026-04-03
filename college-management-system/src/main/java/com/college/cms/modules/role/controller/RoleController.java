package com.college.cms.modules.role.controller;

import com.college.cms.common.base.dto.ApiResponse;
import com.college.cms.modules.role.dto.RoleRequest;
import com.college.cms.modules.role.entity.Role;
import com.college.cms.modules.role.service.RoleService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ApiResponse<Role> createRole(
            @Valid
            @RequestBody RoleRequest param
    ) {
        Role role = roleService.createRole(param);
        return ApiResponse.<Role>builder()
                .success(true)
                .message("Role created successfully")
                .errorCode(null)
                .data(role)
                .build();
    }

}