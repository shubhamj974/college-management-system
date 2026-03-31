package com.college.cms.modules.auth.entity;

import com.college.cms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // ADMIN, STUDENT
    private String code;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "role")
    private Set<RolePermission> rolePermissions;
}