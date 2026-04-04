package com.college.cms.modules.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionDto {

    private Long id;

    @NotBlank(message = "Permission name is required")
    private String name;

    @NotBlank(message = "Permission code is required")
    private String code;
}
