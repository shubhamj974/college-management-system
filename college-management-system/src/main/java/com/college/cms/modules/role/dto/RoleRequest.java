package com.college.cms.modules.role.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank(message = "Role name is required")
    private String name;

    @NotEmpty(message = "At least one permission is required")
    @Valid
    private List<RolePermissionDto> permissions;
}
