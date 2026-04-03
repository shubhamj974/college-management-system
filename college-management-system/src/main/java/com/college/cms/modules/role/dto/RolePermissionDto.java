package com.college.cms.modules.role.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionDto {
    @NotNull
    Long permissionId;
    private String permissionName;

    @NotNull
    Long moduleId;
    private String moduleName;
}
