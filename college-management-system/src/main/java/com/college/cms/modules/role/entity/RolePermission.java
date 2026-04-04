package com.college.cms.modules.role.entity;

import com.college.cms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "moduleId")
    private ModuleName module;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permissionId")
    private Permission permission;
}