package com.college.cms.modules.role.controller;

import com.college.cms.common.base.dto.ApiResponse;
import com.college.cms.modules.role.dto.PermissionDto;
import com.college.cms.modules.role.entity.Permission;
import com.college.cms.modules.role.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Permission>> createPermission(
            @Valid @RequestBody PermissionDto param
    ) {
        Permission permission = permissionService.createPermission(param);

        ApiResponse<Permission> response = ApiResponse.<Permission>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message("Permission created successfully")
                .data(permission)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Permission>>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();

        ApiResponse<List<Permission>> response = ApiResponse.<List<Permission>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Permissions fetched successfully")
                .data(permissions)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);

        ApiResponse<Permission> response = ApiResponse.<Permission>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Permission fetched successfully")
                .data(permission)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionDto param
    ) {
        Permission permission = permissionService.updatePermission(id, param);

        ApiResponse<Permission> response = ApiResponse.<Permission>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Permission updated successfully")
                .data(permission)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Permission deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}
