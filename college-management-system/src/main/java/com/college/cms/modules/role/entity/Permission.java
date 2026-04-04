package com.college.cms.modules.role.entity;

import com.college.cms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {

    @Column(unique = true)
    private String name;

    private String code;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> rolePermissions;
}