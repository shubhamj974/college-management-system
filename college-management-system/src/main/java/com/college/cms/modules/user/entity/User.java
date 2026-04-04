package com.college.cms.modules.user.entity;

import com.college.cms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String employeeId;

    private String gender;

    private LocalDate dateOfBirth;

    private String profilePicture;

    private String address;

    private String city;

    private String state;

    private String pincode;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_email_verified")
    @Builder.Default
    private Boolean isEmailVerified = false;

    private LocalDateTime lastLoginAt;

    private String resetToken;

    private LocalDateTime resetTokenExpiry;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles;
}