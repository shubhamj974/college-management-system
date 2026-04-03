package com.college.cms.modules.role.entity;

import com.college.cms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // CREATE, READ, UPDATE, DELETE

    private String code;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> rolePermissions;
}