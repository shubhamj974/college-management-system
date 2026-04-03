package com.college.cms.modules.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleDto {

    private Long id;
    @NotBlank
    private String name;
    private Long parentId;
    @NotBlank
    private String code;
    @NotBlank
    private String routePath;
    private String icon;
    @NotNull
    private Integer displayOrder;
    private List<ModuleDto> children;
}