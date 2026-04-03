package com.college.cms.modules.role.repository;

import com.college.cms.modules.role.entity.ModuleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<ModuleName, Long> {
    Optional<ModuleName> findByName(String name);
    boolean existsByName(String name);
}
