package com.college.cms.modules.role.service;

import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.dto.PermissionDto;
import com.college.cms.modules.role.entity.Permission;
import com.college.cms.modules.role.repository.PermissionRepositrory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepositrory permissionRepositrory;

    public Permission createPermission(PermissionDto param) {
        if (permissionRepositrory.existsByName(param.getName())) {
            throw new CustomException(
                    "Permission name already exists",
                    HttpStatus.CONFLICT,
                    "PERMISSION_001"
            );
        }

        Permission permission = Permission.builder()
                .name(param.getName())
                .code(param.getCode())
                .build();

        return permissionRepositrory.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepositrory.findAll();
    }

    public Permission getPermissionById(Long id) {
        return permissionRepositrory.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Permission not found",
                        HttpStatus.NOT_FOUND,
                        "PERMISSION_404"
                ));
    }

    public Permission updatePermission(Long id, PermissionDto param) {
        Permission permission = getPermissionById(id);

        // Check if name exists for another permission
        if (!permission.getName().equals(param.getName())
                && permissionRepositrory.existsByName(param.getName())) {
            throw new CustomException(
                    "Permission name already exists",
                    HttpStatus.CONFLICT,
                    "PERMISSION_001"
            );
        }

        permission.setName(param.getName());
        permission.setCode(param.getCode());

        return permissionRepositrory.save(permission);
    }

    public void deletePermission(Long id) {
        Permission permission = getPermissionById(id);
        permissionRepositrory.delete(permission);
    }
}
