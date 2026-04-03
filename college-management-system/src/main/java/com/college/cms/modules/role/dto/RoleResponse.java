package com.college.cms.modules.role.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RoleResponse {

    private Long id;
    private String name;
    private String code;

    private List<RolePermissionDto> permissions;
}