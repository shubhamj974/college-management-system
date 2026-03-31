package com.college.cms.modules.auth.entity;

import com.college.cms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleName module;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}