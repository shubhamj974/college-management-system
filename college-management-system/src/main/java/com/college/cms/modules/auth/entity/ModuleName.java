package com.college.cms.modules.auth.entity;

import com.college.cms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "module_name")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleName extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // STUDENT, FEES, COURSE

    @OneToMany(mappedBy = "module")
    private Set<RolePermission> rolePermissions;
}