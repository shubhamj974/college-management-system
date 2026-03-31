package com.college.cms.modules.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import com.college.cms.common.base.BaseEntity;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles;
}