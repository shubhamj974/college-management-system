package com.college.cms.modules.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String code;
}
