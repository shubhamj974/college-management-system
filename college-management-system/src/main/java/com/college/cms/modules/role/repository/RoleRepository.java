package com.college.cms.modules.role.repository;

import com.college.cms.common.base.enums.RoleType;
import com.college.cms.modules.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}