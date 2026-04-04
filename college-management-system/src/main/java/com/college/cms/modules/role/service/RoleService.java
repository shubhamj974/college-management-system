package com.college.cms.modules.role.service;

import com.college.cms.common.base.enums.RoleType;
import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.dto.RolePermissionDto;
import com.college.cms.modules.role.dto.RoleRequest;
import com.college.cms.modules.role.dto.RoleResponse;
import com.college.cms.modules.role.entity.ModuleName;
import com.college.cms.modules.role.entity.Permission;
import com.college.cms.modules.role.entity.Role;
import com.college.cms.modules.role.entity.RolePermission;
import com.college.cms.modules.role.repository.ModuleRepository;
import com.college.cms.modules.role.repository.PermissionRepositrory;
import com.college.cms.modules.role.repository.RolePermissionRepository;
import com.college.cms.modules.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final ModuleRepository moduleRepository;
    private final PermissionRepositrory permissionRepositrory;

    @Transactional
    public RoleResponse createRole(RoleRequest param) {
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

        role = roleRepository.save(role);
        saveRolePermissions(role, param.getPermissions());

        return buildRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::buildRoleResponse)
                .toList();
    }

    public RoleResponse getRoleById(Long id) {
        Role role = findRoleById(id);
        return buildRoleResponse(role);
    }

    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest param) {
        Role role = findRoleById(id);

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

        // Check if name exists for another role
        if (!role.getName().equals(roleType.getDisplayName())
                && roleRepository.existsByName(roleType.getDisplayName())) {
            throw new CustomException(
                    "Role already exists",
                    HttpStatus.CONFLICT,
                    "ROLE_001"
            );
        }

        role.setName(roleType.getDisplayName());
        role.setCode(roleType.name());
        role = roleRepository.save(role);

        // Delete existing permissions and add new ones
        rolePermissionRepository.deleteByRoleId(id);
        saveRolePermissions(role, param.getPermissions());

        return buildRoleResponse(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        Role role = findRoleById(id);
        rolePermissionRepository.deleteByRoleId(id);
        roleRepository.delete(role);
    }

    private Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Role not found",
                        HttpStatus.NOT_FOUND,
                        "ROLE_404"
                ));
    }

    private void saveRolePermissions(Role role, List<RolePermissionDto> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return;
        }

        for (RolePermissionDto rp : permissions) {
            ModuleName module = moduleRepository.findById(rp.getModuleId())
                    .orElseThrow(() -> new CustomException(
                            "Module not found",
                            HttpStatus.NOT_FOUND,
                            "MODULE_404"
                    ));

            Permission permission = permissionRepositrory.findById(rp.getPermissionId())
                    .orElseThrow(() -> new CustomException(
                            "Permission not found",
                            HttpStatus.NOT_FOUND,
                            "PERMISSION_404"
                    ));

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setModule(module);
            rolePermission.setPermission(permission);

            rolePermissionRepository.save(rolePermission);
        }
    }

    private RoleResponse buildRoleResponse(Role role) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(role.getId());

        List<RolePermissionDto> permissionResponses = rolePermissions.stream()
                .map(rp -> RolePermissionDto.builder()
                        .moduleId(rp.getModule().getId())
                        .moduleName(rp.getModule().getName())
                        .permissionId(rp.getPermission().getId())
                        .permissionName(rp.getPermission().getName())
                        .build()
                ).toList();

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .permissions(permissionResponses)
                .build();
    }
}