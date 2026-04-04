package com.college.cms.modules.role.controller;

import com.college.cms.common.base.dto.ApiResponse;
import com.college.cms.modules.role.dto.RoleRequest;
import com.college.cms.modules.role.dto.RoleResponse;
import com.college.cms.modules.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @Valid @RequestBody RoleRequest param
    ) {
        RoleResponse role = roleService.createRole(param);

        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message("Role created successfully")
                .data(role)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();

        ApiResponse<List<RoleResponse>> response = ApiResponse.<List<RoleResponse>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Roles fetched successfully")
                .data(roles)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        RoleResponse role = roleService.getRoleById(id);

        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Role fetched successfully")
                .data(role)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest param
    ) {
        RoleResponse role = roleService.updateRole(id, param);

        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Role updated successfully")
                .data(role)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Role deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}