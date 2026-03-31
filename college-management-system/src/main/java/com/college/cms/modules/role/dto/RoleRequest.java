package com.college.cms.modules.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    @NotBlank()
    private String name;
    @NotBlank()
    private String code;
}
