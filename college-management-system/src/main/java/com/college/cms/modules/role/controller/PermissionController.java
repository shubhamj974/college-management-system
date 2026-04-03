package com.college.cms.modules.role.controller;
import com.college.cms.common.base.dto.ApiResponse;
import com.college.cms.modules.role.dto.PermissionDto;
import com.college.cms.modules.role.entity.Permission;
import com.college.cms.modules.role.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping()
    public ApiResponse<Permission> createPermission(
            @Valid
            @RequestBody PermissionDto param
    ){

        Permission permission = permissionService.createPermission(param);
            return ApiResponse.<Permission>builder()
                    .success(true)
                    .message("Permission created successfully.")
                    .errorCode(null)
                    .data(permission)
                    .build();
    }
}
