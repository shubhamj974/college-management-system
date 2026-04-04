package com.college.cms.modules.role.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionDto {

    @NotNull(message = "Permission ID is required")
    private Long permissionId;
    private String permissionName;

    @NotNull(message = "Module ID is required")
    private Long moduleId;
    private String moduleName;
}
