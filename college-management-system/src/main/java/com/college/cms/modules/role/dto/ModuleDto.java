package com.college.cms.modules.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleDto {

    private Long id;

    @NotBlank(message = "Module name is required")
    private String name;

    private Long parentId;

    @NotBlank(message = "Module code is required")
    private String code;

    @NotBlank(message = "Route path is required")
    private String routePath;

    private String icon;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;

    private List<ModuleDto> children;
}