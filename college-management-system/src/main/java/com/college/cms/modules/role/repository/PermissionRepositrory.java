package com.college.cms.modules.role.repository;
import com.college.cms.modules.role.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PermissionRepositrory extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
}
