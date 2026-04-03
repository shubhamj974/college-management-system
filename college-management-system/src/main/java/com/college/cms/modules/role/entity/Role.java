package com.college.cms.modules.role.entity;

import com.college.cms.common.base.BaseEntity;
import com.college.cms.modules.user.entity.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private String code;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "role")
    private Set<RolePermission> rolePermissions;
}