package com.college.cms.modules.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    @NotBlank()
    private String name;

    @NotEmpty
    private List<RolePermissionDto> permissions;
}
