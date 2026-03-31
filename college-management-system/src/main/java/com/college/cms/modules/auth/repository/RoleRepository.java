package com.college.cms.modules.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.college.cms.modules.auth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}